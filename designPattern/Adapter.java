package designPattern;

/*The Adapter Design Pattern is a structural design pattern that allows 
incompatible interfaces to work together by converting the interface 
of one class into another that the client expects.*/

// Target Interface
interface MediaPlayer{
	void play(String filename);
}

class Mp3Player implements MediaPlayer{
	@Override
	public void play(String filename) {
		System.out.println("Mp3 Player : Playing " + filename);
	}
}

// External Libraries (Adaptees)
class VlcCodec{
	void playVlc(String filename) {
		System.out.println("VLC Codec: Decoding and playing " + filename);
	}
}

class Mp4Codec{
	void playMp4(String filename) {
		System.out.println("Mp4 Codec: Decoding and playing " + filename);
	}
}

// Adapters
class VlcPlayerAdapter implements MediaPlayer{
	private final VlcCodec codec;
	
	public VlcPlayerAdapter(VlcCodec codec) {
		this.codec = codec;
	}

	@Override
	public void play(String filename) {
		codec.playVlc(filename);
	}
}

class Mp4PlayerAdapter implements MediaPlayer{
	private final Mp4Codec codec;
	
	public Mp4PlayerAdapter(Mp4Codec codec) {
		this.codec = codec;
	}

	@Override
	public void play(String filename) {
		codec.playMp4(filename);
	}
}

// Client
class AudioPlayer{
	public void playFile(String filename) {
		MediaPlayer player;
		String extension = filename.substring(filename.lastIndexOf('.') + 1);
//		System.out.println(extension);
		switch(extension) {
			case "mp3":
				player = new Mp3Player();
				break;
			case "vlc":
				player = new VlcPlayerAdapter(new VlcCodec());
				break;
			case "mp4":
				player = new Mp4PlayerAdapter(new Mp4Codec());
				break;
			default:
				System.out.println("Unsupported Format: " + extension);
				return;
				
		}
		player.play(filename);
	}
}

public class Adapter {
	public static void main(String[] args) {
		AudioPlayer player = new AudioPlayer();
		player.playFile("song.mp3");
        player.playFile("movie.mp4");
        player.playFile("documentary.vlc");
        player.playFile("image.png");
	}
}
