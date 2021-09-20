package cs1501_p5;

import java.math.BigInteger;

public class MyTestFile{
    public static void main(String[] args){
        //shiftTest();
        //singleDigitMultTest();
        //gradeSchoolMultTest();
        //divisionTest();
        //modTest();
        //equalsTest();
        gcdTest();
        //contractTest();
        }


    private static void gcdTest(){
        //HeftyInteger a = new HeftyInteger(new BigInteger("45986321701524315216459863217015243152164598632170152431521645986321701524315216459863217015243152164598632170152431521645986321701524315216459863217015243152164598632170152431521645986321701524315216").toByteArray());
        //HeftyInteger b = new HeftyInteger(new BigInteger("15243121701524315216459843152117015264598632170152431521647015243152164598632170152431521645984315216").toByteArray());
        HeftyInteger a = new HeftyInteger(new BigInteger("135").toByteArray());
        HeftyInteger b = new HeftyInteger(new BigInteger("385").toByteArray());
        HeftyInteger[] result = a.XGCD(b);
        System.out.println("gcd: " + new BigInteger(result[0].getVal()));
        System.out.println("s: " + new BigInteger(result[1].getVal()));
        System.out.println("t: " + new BigInteger(result[2].getVal()));
        
    }

    private static void equalsTest(){
        HeftyInteger a = new HeftyInteger(new BigInteger("456").toByteArray());
        HeftyInteger b = new HeftyInteger(new BigInteger("255").toByteArray());
        System.out.println(a.equals(b));

        a = new HeftyInteger(new BigInteger("400").toByteArray());
        b = new HeftyInteger(new BigInteger("400").toByteArray());
        System.out.println(a.equals(b));

        a = new HeftyInteger(new byte[]{4});
        b = new HeftyInteger(new byte[]{0,4});
        System.out.println(a.equals(b));

    }
        
    private static void modTest() {
        HeftyInteger a = new HeftyInteger(new BigInteger("45679842").toByteArray());
        HeftyInteger b = new HeftyInteger(new BigInteger("255").toByteArray());
        //HeftyInteger c = a.mod(b);

        BigInteger abi = new BigInteger(a.getVal());
        BigInteger bbi = new BigInteger(b.getVal());
        BigInteger cbi = abi.mod(bbi);

        //System.out.println("HeftyInteger: " + new BigInteger(c.getVal()));
        System.out.println("BigInteger:   " + cbi);
    }




    private static void divisionTest() {
        HeftyInteger a = new HeftyInteger(new BigInteger("45679842").toByteArray());
        HeftyInteger b = new HeftyInteger(new BigInteger("255").toByteArray());
        //HeftyInteger c = a.dividedBy(b);

        BigInteger abi = new BigInteger(a.getVal());
        BigInteger bbi = new BigInteger(b.getVal());
        BigInteger cbi = abi.divide(bbi);

        //System.out.println("HeftyInteger: " + new BigInteger(c.getVal()));
        System.out.println("BigInteger:   " + cbi);

        //System.out.println(cbi.equals(new BigInteger(c.getVal())) + "\n\n");
    }




    private static void shiftTest() {
        //HeftyInteger a = new HeftyInteger(new byte[]{0,(byte)0b11111010});
        HeftyInteger a = new HeftyInteger(new byte[]{125});
        int mov = 1;

        System.out.println("Original a: ");
        printVal(a);

        System.out.println("\nRight shift a by " + mov + ": ");
        printVal(a.rightShift(mov));

        
        System.out.println("\nLeft shift a by " + mov + ": ");
        printVal(a.leftShift(mov));
        

    }
    

    private static void gradeSchoolMultTest() {
        
        HeftyInteger a = new HeftyInteger(new BigInteger("8657923432165798543249654987321372431321").toByteArray());
        HeftyInteger b = new HeftyInteger(new BigInteger("865").toByteArray());
        HeftyInteger c = a.multiply(b);

        BigInteger abi = new BigInteger(a.getVal());
        BigInteger bbi = new BigInteger(b.getVal());
        BigInteger cbi = abi.multiply(bbi);

        System.out.println("HeftyInteger: " + new BigInteger(c.getVal()));
        System.out.println("BigInteger:   " + cbi);

        System.out.println(cbi.equals(new BigInteger(c.getVal())) + "\n\n");

        a = new HeftyInteger(new byte[]{99});
        b = new HeftyInteger(new byte[]{-125});
        c = a.multiply(b);

        abi = new BigInteger(a.getVal());
        bbi = new BigInteger(b.getVal());
        cbi = abi.multiply(bbi);

        System.out.println("HeftyInteger: " + new BigInteger(c.getVal()));
        System.out.println("BigInteger:   " + cbi);

        System.out.println(cbi.equals(new BigInteger(c.getVal())) + "\n\n");



        a = new HeftyInteger(new byte[]{9});
        b = new HeftyInteger(new byte[]{-4});
        c = a.multiply(b);

        abi = new BigInteger(a.getVal());
        bbi = new BigInteger(b.getVal());
        cbi = abi.multiply(bbi);

        System.out.println("HeftyInteger: " + new BigInteger(c.getVal()));
        System.out.println("BigInteger:   " + cbi);
        System.out.println(cbi.equals(new BigInteger(c.getVal())) + "\n\n");



        a = new HeftyInteger(new byte[]{120});
        b = new HeftyInteger(new byte[]{4});
        c = a.multiply(b);

        abi = new BigInteger(a.getVal());
        bbi = new BigInteger(b.getVal());
        cbi = abi.multiply(bbi);

        System.out.println("HeftyInteger: " + new BigInteger(c.getVal()));
        System.out.println("BigInteger:   " + cbi);

        System.out.println(cbi.equals(new BigInteger(c.getVal())) + "\n\n");


        /*
        c = new HeftyInteger(new byte[]{4});
        System.out.println("HeftyInteger 4: " + new BigInteger(c.getVal()));
        c = c.negate();
        System.out.println("HeftyInteger -4: " + new BigInteger(c.getVal()));
        */
    }



    private static void printVal(HeftyInteger arg){
        System.out.println("HeftyInteger value: " + new BigInteger(arg.getVal()));
    }

}