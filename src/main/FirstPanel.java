package main;

import javax.swing.JFrame;
import login.*;
 
public class FirstPanel{
    Login login;
    Register register;
    MainInterface mainI;
   
    public static void main(String[] args) {
       
        // 메인클래스 실행
    	FirstPanel main = new FirstPanel();
        main.login = new Login(); // 로그인창 보이기
        main.login.setMain(main); // 로그인창에게 메인 클래스보내기
    }
   
    // 테스트프레임창
    public void showMainI(){
        login.dispose(); // 로그인창닫기
        this.mainI = new MainInterface(); // 테스트프레임 오픈
    }
    public void showRegFrm() {
    	this.register = new Register();
    }
 
}