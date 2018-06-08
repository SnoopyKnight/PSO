import java.util.Scanner;
import java.util.*;
import java.lang.*;
import static java.lang.Math.*;
import java.util.concurrent.ThreadLocalRandom;

public class pso1{
    public static int num_iter = 10000;
    public static int num_birds =10000;
    public static int dim = 2;
    public static double w = 1.0;
    public static double c_1 = 0.8;
    public static double c_2 = 0.8;
    public static double position_X[][] = new double[num_birds][dim];  //input value
    public static double position_Y[] = new double[num_birds];  //every birds initial position
    public static double each_position[][] = new double[num_birds][num_iter];   //birds[i],position[i]
    public static double each_velocity[][] = new double[num_birds][num_iter];   //birds[i],velocity[i]
    public static double pbest[] = new double[num_birds];   //every birds[i]'s best position
    public static double gbest = 999999999;
    public static double t = 1.0;
    public static double vmax = 0.0;
    public static double X_min = -32.768;
    public static double X_max = 32.768;


    public static void main(String[] args) {

        //create position_x
        for(int a = 0; a < num_birds; a++) {
            for (int b = 0; b < dim; b++) {
                position_X[a][b] = ThreadLocalRandom.current().nextDouble(X_min, X_max);
                //System.out.println(position_X[a][b]);
            }
        }

        //create position_y(dataset)
        for(int a = 0; a < num_birds; a++) {
            position_Y[a] = Ackley_funct(position_X[a],2);
        }

        for(int i=0;i<num_birds;i++){
            System.out.print(position_Y[i]+" , ");
        }
        System.out.println(" ");

        //create birds[i],position[1]
        for(int a = 0; a < num_birds; a++){
            each_position[a][0] = position_Y[a];
            for(int b = 1; b < num_iter; b++){
                each_position[a][b] = 999999999;
            }
        }

//        for(int a = 0; a < num_birds; a++){
//            for(int b = 0; b < num_iter; b++){
//                System.out.print(each_position[a][b] + " , ");
//            }
//            System.out.println(" ");
//        }

        //initial each birds' pbest
        for(int i = 0; i < num_birds; i++){
            pbest[i] = each_position[i][0];
            System.out.println(pbest[i]);
        }
        gbest = getMin(pbest);
        System.out.print("gbest = " + gbest);


        //initial all velocity to 0.1
        for(int a = 0; a < num_birds; a++){
            for(int b = 0; b < num_iter; b++){
                each_velocity[a][b] = 0.0;
            }
        }

        //limit vmax
        vmax = getMax(position_Y) - getMin(position_Y);
        double rnt = ThreadLocalRandom.current().nextDouble(0.0001, 0.05);
        vmax = rnt*vmax;

        //update
        for(int i = 0; i < num_iter; i++){
            for(int j = 1; j < num_birds; j++){  //every birds
                each_velocity[j][i] = w*each_velocity[j-1][i] + c_1*(pbest[i] - each_velocity[j-1][i]) + c_2*(pbest[i] - each_velocity[j-1][i]);
                if(each_velocity[j][i] > vmax){
                    each_velocity[j][i] = vmax;
                }
                each_position[j][i] = each_position[j-1][i] + each_velocity[j][i]*t;
                if(each_position[j][i] < pbest[i]){
                    pbest[i] = each_position[j][i];
                }
            }
            gbest = getMin(pbest);
            System.out.print(gbest);
        }
//        System.out.print("final gbest = " + gbest);


    }




    public static double Ackley_funct(double[] x, int d){
        int a = 20;
        double b = 0.2;
        double c = 2*PI;
        double sum_1=0.0;
        double sum_2=0.0;
        for(int i=0; i<d; i++){
            sum_1 = sum_1 + pow(x[i],2);
        }
        sum_1 = (-1*a)*(exp((-1*b)*(sqrt(sum_1/d))));

        for(int i=0; i<d; i++){
            sum_2 = sum_2 + cos(c*x[i]);
        }
        sum_2 = -1*(exp(sum_2/d));
        return  sum_1 +sum_2 + a + exp(1.0);
    }

    // Method for getting the minimum value
    public static double getMin(double[] inputArray){
        double minValue = inputArray[0];
        for(int i=1;i<inputArray.length;i++){
            if(inputArray[i] < minValue){
                minValue = inputArray[i];
            }
        }
        return minValue;
    }

    // Method for getting the maximum value
    public static double getMax(double[] inputArray){
        double maxValue = inputArray[0];
        for(int i=1;i < inputArray.length;i++){
            if(inputArray[i] > maxValue){
                maxValue = inputArray[i];
            }
        }
        return maxValue;
    }

//
}