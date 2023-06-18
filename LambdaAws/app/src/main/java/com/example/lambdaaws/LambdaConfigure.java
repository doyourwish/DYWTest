package com.example.lambdaaws;

import com.amazonaws.regions.Regions;

public class LambdaConfigure{
    //Python or JavaScript
    String language = "Python";
    String identityPoolId = "us-west-2:868e1fbc-14c2-4e7a-847a-ec74f54ce359";
    Regions cognitoRegion = Regions.US_WEST_2;
    Regions lambdaRegion = Regions.US_WEST_2;
}
