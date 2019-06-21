package pooja.devlal.com.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
public class Calculator extends AppCompatActivity {
    private static TextView dplay;
    private int[] numericButtons={R.id.num0,R.id.num1,R.id.num2,R.id.num3,R.id.num4,R.id.num5,R.id.num6,R.id.num7,R.id.num8,R.id.num9};
    private int[] operatorButtons={R.id.divide,R.id.multiply,R.id.minus,R.id.sum,R.id.percent};
    private boolean lastNumeric;
    private boolean stateError;
    private boolean lastDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        dplay = (TextView) findViewById(R.id.display);

        setNumericOnClickListner();
        setOperatorOnClickListner();
    }
    private void setNumericOnClickListner() {
        View.OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (stateError) {
                    dplay.setText(button.getText());
                    stateError = false;
                } else {
                    dplay.append(button.getText());
                }
                lastNumeric = true;
            }
        };
        //Assign the listner to all the numeric buttons
        for (int id : numericButtons) {
            findViewById(id).setOnClickListener(listener);
        }
    }
        //find and set Onclicklistener to operator buttons and other left buttons...

        private void setOperatorOnClickListner(){
            //create a common OnClickListner for operators
            View.OnClickListener listener=new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(lastNumeric &&  !stateError){
                        Button button=(Button)v;
                        dplay.append(button.getText());
                        lastNumeric=false;
                        lastDot=false;

                    }
                }
            };
        //assign listener to all the operator nuttons
            for(int id:operatorButtons){
                findViewById(id).setOnClickListener(listener);
            }
            //Decimal point
            findViewById(R.id.decimal).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(lastNumeric && !stateError && !lastDot){
                        dplay.append(".");
                        lastNumeric=false;
                        lastDot=true;
                    }
                }
            });
         /*  //alter button
            findViewById(R.id.sign_alter).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String value=dplay.getText();
                    if(){

                    }
                }
            })*/
            //clear button
            findViewById(R.id.clear).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dplay.setText("");
                    //reset all the states and flags
                    lastNumeric=false;
                    stateError=false;
                    lastDot=false;
                }
            });

            //Delete button
            findViewById(R.id.delete).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str=dplay.getText().toString();
                    if(str.length()>=1){
                        str=str.substring(0,str.length()-1);
                        dplay.setText(str);
                    }
                    if(str.length()<1)
                    {
                        dplay.setText("");
                    }


                }

            });
            //open Next activity to use scientific calculator
            findViewById(R.id.scientific).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Calculator.this,"Scientific Calculator",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Calculator.this,Scientific.class));
                }
            });
            //Equal button
            findViewById(R.id.equals).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEqual();
                }
            });



    }
    //logic to calculate the solution
    private void onEqual(){
        if(lastNumeric && !stateError){
            //read the expression
            String txt=dplay.getText().toString();
            //Create an expression (A class from exp4j library)
            Expression expression=new ExpressionBuilder(txt).build();
            try{
                //calculate the result and display
                double result=expression.evaluate();
                    dplay.setText(Double.toString(result));
                        lastDot=true;
                //result cintains a dot
            }
            catch(ArithmeticException ex){
                dplay.setText("Error");
                stateError=true;
                lastNumeric=false;
            }
        }
    }

}
