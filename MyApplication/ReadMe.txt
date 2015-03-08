BBBBB                  iii         tt                            DDDDD                             
BB   B  rr rr    aa aa     nn nnn  tt    rr rr    eee    eee     DD  DD    eee  mm mm mmmm   oooo  
BBBBBB  rrr  r  aa aaa iii nnn  nn tttt  rrr  r ee   e ee   e    DD   DD ee   e mmm  mm  mm oo  oo 
BB   BB rr     aa  aaa iii nn   nn tt    rr     eeeee  eeeee     DD   DD eeeee  mmm  mm  mm oo  oo 
BBBBBB  rr      aaa aa iii nn   nn  tttt rr      eeeee  eeeee    DDDDDD   eeeee mmm  mm  mm  oooo  
                                                                                                   
@author: Daniel Park
@date : March 7, 2015

=UX=
-When you first launch the app, the credit card field will have blinking cursor to prompt you to enter credit card number. Upon completing 15 digits, it will check if it is Amex number. If it is, then the card logo changes to amex. If the user enters the 16th digit, then it checks whether it is mastercard, visa or discover or JCB, and updates the logo perspectively. If the number does not match any of these networks, just show generic card logo.

-Then the user taps on expiration edit text and enters in the date. The date cannot be from the past from current systemtime. 

-Then the user taps on CVV field and etners the 3 or 4 fields. If Amex was detected from step 1 then the field is limited to 4 digits, otherwise 3 digit limit. 

-Upon clicking the submit button, it validates all the fields and shows a friendly error message(s). These messages are designed for end-user not developers. If all the validations pass, then it shows a congratulatory message.


=Testing=
To run the tests: right click on braintreedemo.ApplicationTest and run "ApplicationTest"
-test coverage:
   5 luhn validation checks