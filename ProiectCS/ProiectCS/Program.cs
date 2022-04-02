﻿using System;
using System.Collections.Generic;
using System.Configuration;
using System.Windows.Forms;
using log4net.Config;
using NET.model;
using NET.repository;

namespace ProiectCS
{
    static class Program
    {
        /*public static void Main(string[] args)
        {   
            XmlConfigurator.Configure(new System.IO.FileInfo(args[0]));
            IDictionary<string, string> props = new SortedList<string, string>();
            props.Add("ConnectionString",GetConnectionStringByName("travelDB"));
        }*/
        
        [STAThread]
        static void Main(string[] args)
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1(args));
        }
        
        

        /*static string GetConnectionStringByName(String name)
        {
            string returnValue = null;
            
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings[name];

            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }*/
    }
}