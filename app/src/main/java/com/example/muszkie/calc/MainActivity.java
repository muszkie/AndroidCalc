package com.example.muszkie.calc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.log4j.*;
import org.apache.log4j.BasicConfigurator;


public class MainActivity extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "com.example.muszkie.calc";
    private final int SIGN_LENGTH = 3;
    private final int TEXT_SIZE = 35;

    private final  String LOGER_FORMAT = "[%p] %c - %m - Date: %d %n Thread: %t - Method: %M - Line: %L - %x";
    private final  String LOGER_MESSAGE = "LOG";

    protected static String history = "";
    private String equation = "";

    private Sign sign;

    private boolean signFlag = false;  //true if sign was written
    private boolean dotFlag = false;   //true if dot was written
    private boolean secondSignFlag = false;

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setTextSize(TEXT_SIZE);

        Layout layout = new PatternLayout( LOGER_FORMAT );
        Appender app1 = new ConsoleAppender( layout );
        BasicConfigurator.configure( app1 );
        Logger logger = Logger.getRootLogger();
        logger.debug( LOGER_MESSAGE );
    }

    public void write( View button ) {
        textView = ( TextView ) findViewById( R.id.textView );
        this.button = ( Button ) button;
        String buttonText = this.button.getText().toString();

        if ( dotPressed( buttonText ) ) {
            if (ifEquationIsNotEmptyAndDotWasntWritten(dotFlag, equation ) ) {
                dotFlag = true;
                textView.setText( textView.getText().toString() + this.button.getText().toString() );
                equation += this.button.getText().toString();
                return;
            } else {
                return;
            }
        }

        if ( ifNoSignWrittenAndWritingSign( buttonText, signFlag ) ) {

            signFlag=true;
            dotFlag = false;
            equation += this.button.getText().toString();
            textView.setText( textView.getText().toString() + this.button.getText().toString() );

        } else if ( ifSignWrittenAndWrittingSign( buttonText, signFlag ) ) {

            if(ifSecondMinusWasntWritten(secondSignFlag, buttonText,  equation ) ) {

                equation += sign.SUBSTRACT.toChar();
                textView.setText( textView.getText().toString() + sign.SUBSTRACT.toMinus() );
                secondSignFlag =true;
                dotFlag =true;

            }else{
                equation += "";
                textView.setText(textView.getText().toString() + "");
            }

        } else if ( ifNoSignWrittenAndWrittingNoSign( buttonText, signFlag ) ) {

            equation += this.button.getText().toString();
            textView.setText(textView.getText().toString() + this.button.getText().toString());

        } else if ( ifSignWrittenAndWrittingNoSign( buttonText, signFlag ) ) {

            signFlag = false;
            secondSignFlag =false;
            dotFlag =false;
            equation += this.button.getText().toString();
            textView.setText( textView.getText().toString() + this.button.getText().toString() );
        }
    }

    //WirteValidation
    public boolean ifNoSignWrittenAndWritingSign(String butText, boolean signFlag){

        return signFlag == false &&
                (butText.equals(sign.ADD.toSign()) ||
                        butText.equals(sign.SUBSTRACT.toSign()) ||
                        butText.equals(sign.MULTIPLY.toSign()) ||
                        butText.equals(sign.DIVIDE.toSign()) ||
                        butText.equals(sign.POW.toSign()));
    }

    public boolean ifSignWrittenAndWrittingSign( String butText, boolean signFlag ){

        return signFlag == true &&
                ( butText.equals( sign.ADD.toSign() ) ||
                        butText.equals( sign.SUBSTRACT.toSign() ) ||
                        butText.equals( sign.MULTIPLY.toSign() ) ||
                        butText.equals( sign.DIVIDE.toSign() ) ||
                        butText.equals( sign.POW.toSign() ) );
    }

    public boolean ifNoSignWrittenAndWrittingNoSign( String butText, boolean signFlag ){
        return signFlag == false &&
                ( !butText.equals( sign.ADD.toSign() ) ||
                        !butText.equals( sign.SUBSTRACT.toSign() ) ||
                        !butText.equals( sign.MULTIPLY.toSign() ) ||
                        !butText.equals( sign.DIVIDE.toSign() ) ||
                        !butText.equals( sign.POW.toSign() ) );
    }

    public boolean ifSignWrittenAndWrittingNoSign( String butText, boolean signFlag ){
        return signFlag == true &&
                ( !butText.equals( sign.ADD.toSign() ) ||
                        !butText.equals( sign.SUBSTRACT.toSign() ) ||
                        !butText.equals( sign.MULTIPLY.toSign() ) ||
                        !butText.equals( sign.DIVIDE.toSign() ) ||
                        !butText.equals( sign.POW.toSign() ) );
    }

    public boolean dotPressed( String butText ){

        return butText.equals( sign.DOT.toString() );
    }

    public boolean ifEquationIsNotEmptyAndDotWasntWritten( boolean dotflag, String equation ){
        return ( equation.length() > 0 && dotflag == false && equation.charAt( equation.length() - 1 ) != ' ' );
    }

    public boolean ifSecondMinusWasntWritten(boolean secondSign, String butText, String equation){
        return secondSign==false &&
                butText.equals( sign.SUBSTRACT.toSign()) &&
                equation.length()> SIGN_LENGTH;
    }
    //WriteValidation



    public void result( View button ) {
        textView = ( TextView ) findViewById( R.id.textView );

        if(ifEquationEqualsEmpty(equation) || textView.length()==1 ) { return; }
        if(ifEquationIsSingleNegativeNumber( equation )){ return; }
        if(ifEquationEqualsSingleSign( equation, signFlag ) ) { return; }
        if(ifSignOnTheBeginingOfEquation( equation,  signFlag)){
           equation =equation.substring(1,2)+equation.substring(3,equation.length());
        }
        if (ifEquationContainsNoSign( equation ) ) { return; }
        if(equation.contains(sign.DOT.toDotSpace())){ return; }


        textView.setText( ReversedPolishNotation.countInRpn( equation ).toString() );


        equation = textView.getText().toString();

        if(equation.contains(".")){ dotFlag =true; }

    }

    //ResultValidation
    public boolean ifEquationEqualsEmpty( String equation ) {
        return equation.equals( "" );
    }

    public boolean ifEquationIsSingleNegativeNumber( String equation ){
        return !equation.contains(" ") && equation.charAt(0)==sign.SUBSTRACT.toChar();
    }

    public boolean ifEquationEqualsSingleSign( String equation, boolean signFlag ){
        return signFlag ||
                equation.charAt( 1 ) == sign.ADD.toChar() && equation.split(" ").length== SIGN_LENGTH ||
                equation.charAt( 1 ) == sign.SUBSTRACT.toChar() && equation.split(" ").length== SIGN_LENGTH ||
                equation.charAt( 1 ) == sign.MULTIPLY.toChar() ||
                equation.charAt( 1 ) == sign.DIVIDE.toChar() ||
                equation.charAt( 1 ) == sign.POW.toChar();
    }

    public boolean ifEquationContainsNoSign( String equation ){
        return !equation.contains( sign.ADD.toString() ) &&
                !equation.contains( sign.SUBSTRACT.toString() ) &&
                !equation.contains( sign.MULTIPLY.toString() ) &&
                !equation.contains( sign.POW.toString() ) &&
                !equation.contains( sign.DIVIDE.toString() );
    }

    public boolean ifSignOnTheBeginingOfEquation(String equation, boolean signFlag){
        return equation.charAt(1)==sign.ADD.toChar() &&
                signFlag==false &&
                equation.length()> SIGN_LENGTH ||
                equation.charAt(1)==sign.SUBSTRACT.toChar() &&
                        signFlag==false &&
                        equation.length()> SIGN_LENGTH;
    }
    //ResultValidation

    public void openHistory( View button ) {
        Intent intent = new Intent( this, HistoryActivity.class );
        intent.putExtra( EXTRA_MESSAGE, history);
        startActivity( intent );
    }

    public static String getExtraMessage() { return EXTRA_MESSAGE; }

    public void clsTextView(View button ) {
        dotFlag = false;
        signFlag=false;
        secondSignFlag =false;
        textView = ( TextView ) findViewById( R.id.textView );
        textView.setText( null );
        equation = "";
    }

    public void clsHistory( View button ) {
        history = "";
    }

}
