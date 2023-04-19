package bryan.tipcalc;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class PrimaryController {

    
     // formatters for currency and percentages
   private static final NumberFormat currency = 
      NumberFormat.getCurrencyInstance();
   private static final NumberFormat percent = 
      NumberFormat.getPercentInstance();
   
   private BigDecimal tipPercentage = new BigDecimal(0.15); // 15% default
    private int numOfPeople = 1; 
    
    
    
    
    @FXML
    private Label tipPercentageLabel;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextField tipTextField;
    @FXML
    private TextField totalTextField;
    @FXML
    private Slider tipPercentageSlider;
    @FXML
    private TextField splitpplnum;
    @FXML
    private TextField splitamount;


    @FXML
    private void calculateButtonPressed(ActionEvent event) {
        
        try {
         BigDecimal amount = new BigDecimal(amountTextField.getText());
         BigDecimal tip = amount.multiply(tipPercentage);
         BigDecimal total = amount.add(tip);
         BigDecimal splitAmount = total.divide(new BigDecimal(numOfPeople), 2, RoundingMode.HALF_UP);



         tipTextField.setText(currency.format(tip));
         totalTextField.setText(currency.format(total));
         splitamount.setText(currency.format(splitAmount));
      }
      catch (NumberFormatException ex) {
         amountTextField.setText("Enter amount");
         amountTextField.selectAll();
         amountTextField.requestFocus();
      }
    }
    
    
     // called by FXMLLoader to initialize the controller
   public void initialize() {
      // 0-4 rounds down, 5-9 rounds up 
      currency.setRoundingMode(RoundingMode.HALF_UP);
      
      // listener for changes to tipPercentageSlider's value
      tipPercentageSlider.valueProperty().addListener(
         new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, 
               Number oldValue, Number newValue) {
               tipPercentage = 
                  BigDecimal.valueOf(newValue.intValue() / 100.0);
               tipPercentageLabel.setText(percent.format(tipPercentage));
            }
         }
      );
  
    splitpplnum.textProperty().addListener((observable, oldValue, newValue) -> {
        try {
           numOfPeople = Integer.parseInt(newValue);
        } catch (NumberFormatException ex) {
           numOfPeople = 1; // set to default
        }
    }); 
    
    
   }   
    
}
