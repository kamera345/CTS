package com.example.cts;

public class Model {

    /* private double death_rate; */

     public void Model(int n, int suspected_cases_today, int suspected_cases_yesterday, int infected_cases_today, int infected_cases_yesterday, int recovered_cases_today, int recovered_cases_yesterday){


        //Total cases of the day
        double alpha = 0.02;
        double total_cases = n / (1 - alpha);

        //Suspected Rate per day

        double suspected_rate = ((float) suspected_cases_today / (float) suspected_cases_yesterday) * 100;

        //infected rate per day
        double infected_rate = ((float) infected_cases_today / (float) infected_cases_yesterday) * 100;

        //recovered rate per day
        double recovered_rate = ((float) recovered_cases_today / (float) recovered_cases_yesterday) * 100;

        //death rate per day
        //double death_rate = (deaths_today/infected_cases_today)*100;

        System.out.println("suspected_rate: " + (float)suspected_rate);
        System.out.println("infected rate: " + (float)infected_rate);
        System.out.println("recovered_rate: " + (float)recovered_rate);
        System.out.println("total_cases:" + (int) total_cases);
    }
}
