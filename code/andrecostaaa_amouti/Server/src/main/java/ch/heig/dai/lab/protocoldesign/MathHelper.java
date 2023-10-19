package ch.heig.dai.lab.protocoldesign;

import java.util.stream.DoubleStream;

public class MathHelper {
    public static double add(double ...listOfValues)
    {
        return DoubleStream.of(listOfValues).reduce(0, Double::sum);
    }

    public static double mul(double ...listOfValues)
    {

        return DoubleStream.of(listOfValues).reduce(1,(a,b)-> a*b);
    }

    public static double div(double numerator, double denominator)
    {

        return numerator / denominator;
    }

    public static double sub(double ...listOfValues)
    {

        return listOfValues[0] - DoubleStream.of(listOfValues).skip(1).sum();
    }

    public static double inv(double value)
    {
        return 1 / value;
    }

    public static double pow(double x, double exponent)
    {
        return Math.pow(x, exponent);
    }
}
