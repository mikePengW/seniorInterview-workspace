package com.chalon.designpattern.facade;

public class HomeTheaterFacade {

	private TheaterLight theaterLight;
	private Popcorn popcorn;
	private Stereo stereo;
	private Projector projector;
	private Screen screen;
	private DVDPlayer dvdPlayer;

	public HomeTheaterFacade() {
		super();
		this.theaterLight = TheaterLight.getInstance();
		this.popcorn = Popcorn.getInstance();
		this.stereo = Stereo.getInstance();
		this.projector = Projector.getInstance();
		this.screen = Screen.getInstance();
		this.dvdPlayer = DVDPlayer.getInstance();
	}

	// 操作分成 4 步

	public void ready() {
		popcorn.on();
		popcorn.pop();
		screen.down();
		projector.on();
		stereo.on();
		dvdPlayer.on();
		theaterLight.dim();
	}

	public void play() {
		dvdPlayer.play();
	}

	public void pause() {
		dvdPlayer.pause();
	}

	public void end() {
		popcorn.off();
		theaterLight.bright();
		screen.up();
		projector.off();
		stereo.off();
		dvdPlayer.off();
	}

}
