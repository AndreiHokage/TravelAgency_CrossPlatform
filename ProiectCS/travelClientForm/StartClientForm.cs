using System;
using System.Windows.Forms;
using travelNetworking;
using travelServices;

namespace ProiectCS
{
    public class StartClientForm
    {
        [STAThread]
        static void Main(string[] args)
        {
            ITravelServices server = new TravelServerObjectProxy("127.0.0.1", 55556);
            ClientCtrl ctrl = new ClientCtrl(server);
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1(ctrl));
        }
    }
}