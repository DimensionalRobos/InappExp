using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using MLApp;

namespace ThresholdGraph
{
    class Program
    {
        static void Main(string[] args)
        {
            MLApp.MLApp matlab = new MLApp.MLApp();
            matlab.Visible = 0;
            matlab.Execute("threshplot("+args[0]+","+args[1]+","+args[2]+")");
            //Console.ReadLine();
        }
    }
}
