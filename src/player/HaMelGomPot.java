package player;

import java.io.FileInputStream;
import java.util.ArrayList;


import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.SampleBuffer;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;

public class HaMelGomPot implements Runnable {
	
	public static final int BUFFER_SIZE = 10000;
	private Decoder decoder;
	private AudioDevice out;
	private ArrayList<Sample> playes;
	private int length;
	
	//플레이 모드 지정
	private Thread thisThread;
	final static int STATE_INIT = 0;
	final static int STATE_STARTED = 1;
	final static int STATE_SUSPENDED = 2;
	final static int STATE_STOPPED = 3;
	
	static int stateCode = STATE_INIT;
	
	
	
	
	// 디코딩 목적
	static class Sample{
		private short[] buffer;
		private int length;
		
		public Sample(short buf[],int s) {
			buffer = buf.clone();
			length = s;
		}
		
		public short[] getBuffer() {
			return buffer;
		}
		public int getLength() {
			return length;
		}
	}

	
	public boolean isInvalid() {
		return (decoder == null || out == null || playes == null || !out.isOpen());
	}
	
	
	//스트림을 통해 샘플링 후 버퍼에 삽입
	protected boolean getPlayes(String path) {
		if(isInvalid())
			return false;
		
		Header header;
		SampleBuffer sb;
		
		System.out.println("현재 실행 중 파일 : "+path);
		
		try(FileInputStream in=new FileInputStream(path);) {
			Bitstream bitStream = new Bitstream(in);
			
			if((header=bitStream.readFrame())==null)
				return false;
			
			while(length < BUFFER_SIZE && header != null) {
				sb = (SampleBuffer) decoder.decodeFrame(header, bitStream);
				playes.add(new Sample(sb.getBuffer(), sb.getBufferLength()));
				length++;
				//기존 스트림 닫기
				bitStream.closeFrame();
				//새로운 스트림 읽어오기
				header=bitStream.readFrame();
			}			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//파일 가져오기
	public boolean open(String path) {
		try {
			decoder = new Decoder();
			out = FactoryRegistry.systemRegistry().createAudioDevice();
			playes = new ArrayList<HaMelGomPot.Sample>(BUFFER_SIZE);
			length = 0;  //0
			out.open(decoder);
			getPlayes(path);
		} catch (JavaLayerException e) {			
			e.printStackTrace();
			decoder = null;
			out = null;
			playes = null;
			return false;
		}
		return true;
	}
	
	
	//play를 위한 메소드
	public void start() {
		System.out.println("노래 시작");
		synchronized (this) {
			thisThread = new Thread(this);
			thisThread.start();
			stateCode = STATE_STARTED;			
		}
	}
	
	@Override
	public void run() {
		System.out.println("노래 시작");
		while (true) {
			if(stateCode == STATE_STOPPED) {
				break;
			}
			play();			
		}
	}
	

	public void play() {
		if(isInvalid()) {
			return;
		}
		System.out.println("실행 중");
		try {
			//프레임을 돌면서 노래 재생
			for(int i=0;i<length;i++) {
				out.write(playes.get(i).getBuffer(), 0,playes.get(i).getLength());
				
				if(stateCode == STATE_STOPPED) {
					close();
				}
				if(stateCode == STATE_SUSPENDED) {
					System.out.println("일시정지");
					while (true) {
						try {
							Thread.sleep(100);							
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						if(stateCode == STATE_STARTED || stateCode == STATE_STOPPED) {
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// play종료
	
	public void close() {
		if(out!=null && !out.isOpen()) {
			out.close();
		}
		length = 0;
		playes = null;
		out = null;
		decoder = null;
	}

	
	//일시정지 
	public void suspend() {
		if(stateCode == STATE_SUSPENDED) {
			return;
		}else if(stateCode == STATE_INIT) {
			System.out.println("실행중 아님");
		}else if(stateCode == STATE_STOPPED) {
			System.out.println("정지 상태");
		}else {
			System.out.println("일시정지");
			stateCode = STATE_SUSPENDED;
		}
	}
	// 되돌리기
	public void resume() {
		if(stateCode == STATE_STARTED || stateCode == STATE_INIT) {
			System.out.println("실행 중 아님");
			return;
		}
		if(stateCode == STATE_STOPPED) {
			System.out.println("정지상태");
		}
		stateCode = STATE_STARTED;
		System.out.println("되돌리기");
	}
	
	//스레드 중지
	
	public void stop() {
		synchronized (this) {
			stateCode = STATE_STOPPED;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
			System.out.println("정지");
		}
	}
}
