package pooja.devlal.com.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Scientific extends AppCompatActivity {
    private static TextView dis;
    private static int[] numButtons = {R.id.num0, R.id.num1, R.id.num2, R.id.num3, R.id.num4, R.id.num5, R.id.num6, R.id.num7, R.id.num8, R.id.num9};
    private static int[] opButtons = {R.id.clear,R.id.delete,R.id.sum,R.id.minus,R.id.divide,R.id.multiply};
    private static int[] mathButtons={R.id.sine, R.id.cos,R.id.tan};
    private static int[] funcButton={R.id.exp,R.id.cap,R.id.log};
    private static boolean lstNumeric;
    private static boolean stateError;
    private static boolean lstDot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scientific);
        dis = (TextView) findViewById(R.id.display);

        setNumericOnClickListner();
        setOperatorOnClickListner();
    }

    private void setNumericOnClickListner() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (stateError) {
                    dis.setText(button.getText());
                    stateError = false;
                } else {
                    dis.append(button.getText());
                }
                lstNumeric = true;
            }
        };
        //Assign the listner to all the numeric buttons
        for (int id : numButtons) {
            findViewById(id).setOnClickListener(listener);
        }
        //sin,cos,tan...
        for(int id:mathButtons){
            findViewById(id).setOnClickListener(listener);
            Toast.makeText(Scientific.this,"This function is set to radian",Toast.LENGTH_SHORT).show();
        }
        //log,exponent,cap...
        for(int id:funcButton){
            findViewById(id).setOnClickListener(listener);
        }


    }

    //find and set Onclicklistener to operator buttons and other left buttons...
    private void setOperatorOnClickListner() {
        //create a common OnClickListner for operators
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lstNumeric && !stateError) {
                    Button button = (Button) v;
                    dis.append(button.getText());
                    lstNumeric = false;
                    lstDot = false;

                }
            }
        };
        //assign listener to all the operator nuttons
        for (int id : opButtons) {
            findViewById(id).setOnClickListener(listener);
        }
        //Decimal point
        findViewById(R.id.decimal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lstNumeric && !stateError && !lstDot) {
                    dis.append(".");
                    lstNumeric = false;
                    lstDot = true;
                }
            }
        });

        //clear button
        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dis.setText("");
                //reset all the states and flags
                lstNumeric = false;
                stateError = false;
                lstDot = false;
            }
        });

        //Delete button
        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = dis.getText().toString();
                if (str.length() >= 1) {
                    str = str.substring(0, str.length() - 1);
                    dis.setText(str);
                }
                if (str.length() < 1) {
                    dis.setText("");
                }


            }

        });


        //Equal button
        findViewById(R.id.equals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqual();
            }
        });

    }

    //logic to calculate the solution
    private void onEqual() {
        if (lstNumeric && !stateError) {
            //read the expression
            String txt = dis.getText().toString();
            //Create an expression (A class from exp4j library)
            Expression expression = new ExpressionBuilder(txt).build();
            try {
                //calculate the result and display
                double result = expression.evaluate();
                dis.setText(Double.toString(result));
                lstDot = true;
                //result cintains a dot
            } catch (ArithmeticException ex) {
                dis.setText("Error");
                stateError = true;
                lstNumeric = false;
            }
        }
    }
}