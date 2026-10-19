package com.mathwake.app;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.text.InputType;
import java.util.Random;
public class ChallengeActivity extends Activity {
 private final Random random=new Random();
 private int solved=0,answer;private boolean demo;
 private TextView progress,question,feedback;private EditText input;
 @Override public void onCreate(Bundle state){super.onCreate(state);
    setShowWhenLocked(true);setTurnScreenOn(true);
    demo=getIntent().getBooleanExtra("demo",false);
    LinearLayout root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(36,65,36,35);root.setGravity(Gravity.CENTER_HORIZONTAL);root.setBackgroundColor(0xFF0C1121);setContentView(root);
    TextView title=text(root,demo?"PRACTICE MODE":"WAKE UP!",33,0xFF76E0C4);
    progress=text(root,"",20,Color.WHITE); progress.setPadding(0,45,0,30);
    question=text(root,"",28,Color.WHITE);question.setPadding(0,15,0,45);
    input=new EditText(this);input.setSingleLine(true);input.setTextColor(Color.WHITE);input.setHintTextColor(0xFF9CA3AF);input.setHint("Your answer");input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_SIGNED);root.addView(input,new LinearLayout.LayoutParams(-1,-2));
    Button submit=new Button(this);submit.setText("CHECK ANSWER");root.addView(submit,new LinearLayout.LayoutParams(-1,-2));
    feedback=text(root,"",17,0xFFFFC86A); feedback.setPadding(0,25,0,0);
    submit.setOnClickListener(v->check());newProblem();
 }
 private TextView text(LinearLayout l,String s,int size,int color){TextView t=new TextView(this);t.setText(s);t.setTextColor(color);t.setTextSize(size);t.setGravity(Gravity.CENTER);l.addView(t);return t;}
 private int n(int min,int max){return min+random.nextInt(max-min+1);}
 private void newProblem(){
    progress.setText("Solved " + solved + " / 3");input.setText("");feedback.setText("");
    int type=random.nextInt(5);String q="";
    switch(type){
      case 0: {int x=n(-9,15),a=n(2,8),b=n(-15,15);answer=x;q="Solve for x:\n"+a+"x "+ (b>=0?"+ ":"- ")+Math.abs(b)+" = "+(a*x+b);break;}
      case 1: {int x=n(2,12),a=n(2,7),b=n(2,9);answer=x;q="Solve for x:\n"+a+"(x + "+b+") = "+(a*(x+b));break;}
      case 2: {int p=n(1,6)*10,k=n(2,10);answer=p*k;q="Find " + p + "% of " + (k*10) + ":";break;}
      case 3: {int multiplier=n(1,4);int[] triples={3,4,5,5,12,13,8,15,17};int i=random.nextInt(3)*3;int a=triples[i]*multiplier,b=triples[i+1]*multiplier;answer=triples[i+2]*multiplier;q="Right triangle:\nlegs = "+a+" and "+b+"\nFind hypotenuse.";break;}
      default: {int a=n(2,12),b=n(2,12);answer=a*a-b*b;q="Evaluate:\n"+a+"² − "+b+"²";break;}
    }
    question.setText(q);input.requestFocus();
 }
 private void check(){try{int value=Integer.parseInt(input.getText().toString().trim());if(value==answer){solved++;if(solved>=3){if(!demo){Intent stop=new Intent(this,AlarmService.class);stop.setAction("STOP_AFTER_MATH");startService(stop);}finish();}else newProblem();}
 else {feedback.setText("Not quite. Try again!");input.selectAll();}}
 catch(NumberFormatException e){feedback.setText("Enter a whole number.");}}
 @Override public void onBackPressed(){if(demo)super.onBackPressed();else feedback.setText("Solve 3 questions to dismiss the alarm.");}
}
