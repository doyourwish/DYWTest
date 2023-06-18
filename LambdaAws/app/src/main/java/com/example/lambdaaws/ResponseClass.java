package com.example.lambdaaws;

public class ResponseClass {
    String greetings;
    int test[];

    public String getGreetings() {
        return greetings;
    }
    public String getTest() {
        String result = "[";
        for (int i = 0; i < test.length; i++) {
            result += test[i];
            if (i < test.length - 1) {
                result += ", ";
            }
        }
        result += "]";
        return result;
    }

    public void setGreetings(String greetings) { this.greetings = greetings; }
    public void setTest(int[] test) {
        this.test = test;
    }

    public ResponseClass(String greetings) {
        this.greetings = greetings;
        test = new int[3];
    }

    public ResponseClass() {
    }
}
