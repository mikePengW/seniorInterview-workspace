package com.chalon.boot.utils.script;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注意：sql必须格式化好，并且字段别名必须由AS指定，并且字段别名如果有引号包裹，引号中不能有空白字符！
 */
public class PinyinUtil {
    /**
     * 判断是否为汉字字符
     *
     * @param c
     * @return
     */
    public Boolean ifHanzi(char c) {
        return Character.toString(c).matches("[\\u4E00-\\u9FA5]+");
    }

    public Boolean ifHanzi(String str) {
        Boolean ifHanzi = false;
        for (char c : str.toCharArray()) {
            if (ifHanzi(c)) {
                ifHanzi = true;
                break;
            } else {
                ifHanzi = false;
            }
        }
        return ifHanzi;
    }

    /**
     * 取汉字拼音的前两位
     *
     * @param str 汉字字符串
     * @return 汉字与其拼音的前两位
     */
    public Map<String, String> getPinYin(String str) {
        if (str == null || str.trim().length() == 0) {
            return new HashMap<String, String>();
        }

        Map<String, String> map = new HashMap<String, String>();

        char[] charArray = str.trim().toCharArray();
        String[] pinyinArray = new String[]{};
        HanyuPinyinOutputFormat format = getFormat();

        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < charArray.length; i++) {
                if (ifHanzi(charArray[i])) {
                    // 将汉字的几种全拼都存到pinyinArray数组中
                    pinyinArray = PinyinHelper.toHanyuPinyinStringArray(charArray[i], format);
                    if (charArray.length == 1) {
                        sb.append(pinyinArray[0]);
                    } else if (toUnicode("省").equals(toUnicode(String.valueOf(charArray[i])))) {
                        sb.append(pinyinArray[0]);
                    } else if (toUnicode("市").equals(toUnicode(String.valueOf(charArray[i])))) {
                        sb.append(pinyinArray[0]);
                    } else {
                        if (pinyinArray[0].length() >= 2) {
                            String target = pinyinArray[0].substring(0, 2);
                            sb.append(target);
                        } else {
                            sb.append(pinyinArray[0]);
                        }
                    }
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }

        String[] numberList = str.split("[\\u4E00-\\u9FA5]+");
        if (numberList.length == 1) {
            map.put(str, sb.toString() + numberList[0]);
        } else if (numberList.length == 2) {
            map.put(str, sb.toString() + numberList[1]);
        } else if ((numberList.length == 0)) {
            map.put(str, sb.toString());
        } else {
            new RuntimeException("你他妈这取的是什么鬼字段名啊！");
        }

        return map;
    }

    public HanyuPinyinOutputFormat getFormat() {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        return format;
    }

    /**
     * 判断字符串数组中是否含有"AS"元素
     *
     * @param array
     * @param flag  AS后面必须是汉字
     * @return
     */
    public int hasAS(String[] array, Boolean flag) {
        List<String> list = new ArrayList<String>();
        for (String str : array) {
            list.add(str.toUpperCase());
        }

        int ifHas = -1;
        for (int i = 0; i < list.size(); i++) {
            if ("AS".equals(list.get(i))) {
                if (flag) {
                    if (ifHanzi(array[i + 1])) {
                        ifHas = i;
                        break;
                    }
                } else {
                    ifHas = i;
                    break;
                }
            } else {
                ifHas = -1;
            }
        }
        return ifHas;
    }

    /**
     * 读旧的sql文件，输出全部改为英文的文件和相应的中英文对照文件
     *
     * @param sqlFileName sql文件名
     */
    public void createTargetFile(String sqlFileName) {
        if (!sqlFileName.endsWith(".sql")) {
            throw new RuntimeException("待处理文件必须是.sql文件！");
        }

        String baseName = sqlFileName.substring(0, sqlFileName.length() - 4);
        List<Map<String, String>> en2cnFile = new ArrayList<Map<String, String>>();

        BufferedReader brOldSql = null;
        BufferedWriter bwNewSql = null;
        BufferedWriter bwEn2cn = null;

        BufferedReader reBrEn2cn = null;
        BufferedReader reBrNewsql = null;
        BufferedWriter reBw = null;
        try {
            brOldSql = new BufferedReader(new InputStreamReader(new FileInputStream(baseName + ".sql"), "UTF-8"));
            bwNewSql = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(baseName + "_tmp.sql"), "UTF-8"));
            bwEn2cn = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(baseName + "_en2cn.txt"), "UTF-8"));
            String line = "";
            while ((line = dealQuotationMark(brOldSql.readLine())) != null) {
                if (ifHanzi(line)) {
                    String[] strs = line.trim().split(" ");
                    if (strs.length >= 3 && hasAS(strs, true) != -1) {
                        String hanzi = strs[hasAS(strs, true) + 1];

                        Map<String, String> map = null;
                        map = getPinYin(dealSymbol(hanzi));
                        if (!en2cnFile.contains(map)) {
                            en2cnFile.add(map);
                        }

                        String cn = map.entrySet().iterator().next().getKey();
                        String en = map.entrySet().iterator().next().getValue();
                        String newLine = line.replace(cn, en);

                        bwNewSql.write(newLine);
                        bwNewSql.write("\n");
                    } else {
                        bwNewSql.write(line);
                        bwNewSql.write("\n");
                    }
                } else {
                    bwNewSql.write(line);
                    bwNewSql.write("\n");
                }
            }

            for (int i = 0; i < en2cnFile.size(); i++) {
                StringBuilder sb = new StringBuilder();
                if (en2cnFile.get(i) != null && !en2cnFile.get(i).isEmpty()) {
                    for (Map.Entry<String, String> entry : en2cnFile.get(i).entrySet()) {
                        if (i < en2cnFile.size() - 1) {
                            sb.append(entry.getKey()).append(",").append(entry.getValue()).append("\n");
                        } else {
                            sb.append(entry.getKey()).append(",").append(entry.getValue());
                        }
                        bwEn2cn.write(sb.toString());
                    }
                }
            }

            bwNewSql.flush();
            bwEn2cn.flush();

            // 第二次处理，处理select语句中tbname.中文字段的情况
            // 重新读取_en2cn.txt文件中的内容，替换_tmp.sql仍然存在的中文
            reBrEn2cn = new BufferedReader(new InputStreamReader(new FileInputStream(baseName + "_en2cn.txt"), "UTF-8"));
            Map<String, String> cnMap = new HashMap<String, String>();
            String cn = "";
            while ((cn = reBrEn2cn.readLine()) != null) {
                if (cn.split(",").length > 1) {
                    cnMap.put(cn.split(",")[0], cn.split(",")[1]);
                }
            }
            String newSql = "";
            reBrNewsql = new BufferedReader(new InputStreamReader(new FileInputStream(baseName + "_tmp.sql"), "UTF-8"));
            reBw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(baseName + "_en.sql"), "UTF-8"));
            while ((newSql = reBrNewsql.readLine()) != null) {
                if (ifHanzi(newSql)) {
                    String[] strs = newSql.trim().split(" ");
                    if (strs.length > 1) {
                        for (String str : strs) {
                            String[] subStrs = str.split("\\.");
                            if (subStrs.length == 2) {
                                if (ifHanzi(subStrs[1])) {
                                    String en = cnMap.get(dealSymbol(subStrs[1]));
                                    if (en != null) {
                                        newSql = newSql.replace(dealSymbol(subStrs[1]), en);
                                    }
                                }
                            }
                        }
                        reBw.write(newSql + "\n");
                    } else if (strs.length == 1) {
                        String[] subStrs = strs[0].split("\\.");
                        if (subStrs.length == 2) {
                            if (ifHanzi(subStrs[1])) {
                                String en = cnMap.get(dealSymbol(subStrs[1]));
                                if (en != null) {
                                    newSql = newSql.replace(dealSymbol(subStrs[1]), en);
                                }
                            }
                            reBw.write(newSql + "\n");
                        } else {
                            reBw.write(newSql + "\n");
                        }
                    }
                } else {
                    reBw.write(newSql + "\n");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResource(brOldSql);
            closeResource(bwNewSql);
            closeResource(bwEn2cn);
            closeResource(reBrEn2cn);
            closeResource(reBrNewsql);
            closeResource(reBw);
            File file = new File(baseName + "_tmp.sql");
            file.delete();
        }
    }

    /**
     * 获取报表涉及到所有子表
     *
     * @param fileName 报表sql文件全路径
     * @param allPath  全系统所有表的记录文件全路径
     */
    public void getSubTable(String fileName, String allPath) {
        String baseName = fileName.substring(0, fileName.length() - 4);

        BufferedReader br = null;
        BufferedReader brAll = null;
        BufferedWriter bw = null;

        List<String> tbList = new ArrayList<String>();
        List<String> allList = new ArrayList<String>();
        try {
            brAll = new BufferedReader(new InputStreamReader(new FileInputStream(allPath)));
            String sub = null;
            while ((sub = brAll.readLine()) != null) {
                allList.add(sub);
            }

            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(baseName + "_subtable.txt"), "UTF-8"));

            String line = null;
            while ((line = br.readLine()) != null) {
                for (String subname : allList) {
                    if (line.toLowerCase().contains(subname)) {
                        if (!tbList.contains(subname)) {
                            tbList.add(subname);
                        }
                    }
                }
            }

            for (String str : tbList) {
                bw.write(str + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(brAll);
            closeResource(br);
            closeResource(bw);
        }
    }

    /**
     * 获取指定表的alter location语句
     *
     * @param hdfsPath hdfs的地址，主机名:端口
     * @param date     location到的时间目录，格式yyyy-MM-dd
     * @param tbPath   需要读取的表名文件的全路径地址
     */
    public void getAlterLocation(String hdfsPath, String date, String tbPath) {
        String baseName = tbPath.substring(0, tbPath.length() - 4);
        String head = "ALTER TABLE ";
        String mid = " SET LOCATION 'hdfs://" + hdfsPath + "/rawdb/business/";//10.253.108.83:8020
        String tail = "/" + date.replace("-", "_") + "';";

        List<String> tabs = new ArrayList<String>();
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            for (int i = 0; i < 10; i++) {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(tbPath), "UTF-8"));
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(baseName + ".alter.sql"), "UTF-8"));
                bw.write("USE business;\n");
                String tbname = null;
                while ((tbname = br.readLine()) != null) {
                    bw.write(head + tbname + mid + tbname + tail + "\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(br);
            closeResource(bw);
        }
    }

    public String dealQuotationMark(String line) {
        String[] strs = {};
        if (line != null) {
            strs = line.split(" ");
        } else {
            return line;
        }

        if (hasAS(strs, false) != -1) {
            String target = dealSymbol(strs[hasAS(strs, false) + 1]);
            int len = target.length();
            int index = line.indexOf(target);
            if ("'".equals(String.valueOf(line.charAt(index - 1))) && "'".equals(String.valueOf(line.charAt(index + len)))) {
                line = line.replace("'" + target + "'", "`" + target + "`");
            }
        }

        return line;
    }

    public String dealSymbol(String line) {
        if (line.contains("'") || line.contains(",") || line.contains("`") || line.contains("，")) {
            line = line.replaceAll("[',`，]", "");
        }
        return line;
    }

    public Boolean hasDeal(String filePath) {
        File file = new File(filePath);
        if (file.isDirectory()) {
            for (File subfile : file.listFiles()) {
                if (subfile.getAbsolutePath().endsWith("_en.sql") || subfile.getAbsolutePath().endsWith("_en2cn.txt")) {
                    return true;
                }
            }
        } else {
            if (filePath.endsWith(".sql")) {
                File subfile01 = new File(filePath.substring(0, filePath.length() - 4) + "_en.sql");
                File subfile02 = new File(filePath.substring(0, filePath.length() - 4) + "_en2cn.txt");
                if (subfile01.exists() || subfile02.exists()) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<String> fileNames = new ArrayList<String>();

    public void createAll(String directoryPath) {
        if (!hasDeal(directoryPath)) {
            File file = new File(directoryPath);

            if (file.isDirectory()) {
                File[] subfiles = file.listFiles();
                for (File subfile : subfiles) {
                    createAll(subfile.getAbsolutePath());
                }
            } else if (directoryPath.endsWith(".sql")) {
                fileNames.add(file.getAbsolutePath());
            } else {
                throw new RuntimeException("目录不存在或者文件格式错误！");
            }

            for (String fileName : fileNames) {
                createTargetFile(fileName);
            }
        } else {
            System.out.println("文件已经处理过！");
        }
    }

    public String toUnicode(String str) {
        String[] arrStr = new String[str.length()];
        String target = "";
        for (int i = 0; i < str.length(); i++) {
            arrStr[i] = Integer.toHexString(str.charAt(i) & 0xffff);
            target += "\\u" + arrStr[i];
        }
        return target;
    }

    public void closeResource(Closeable res) {
        if (res != null) {
            try {
                res.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                res = null;
            }
        }
    }

    public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination, IOException {
        PinyinUtil pu = new PinyinUtil();
//        pu.createAll("C:\\Users\\Dell\\Desktop\\资金流原SQL.sql");
//        pu.getSubTable("C:/Users/Dell/Desktop/repo_finance_payment.spark.sql","C:/Users/Dell/Desktop/易日升项目/一阶段/新系统所有表列表/all.txt");
//        pu.getAlterLocation("10.253.108.83:8020","2017-03-21","C:/Users/Dell/Desktop/repo_finance_payment.spark_subtable.txt");
    }
}