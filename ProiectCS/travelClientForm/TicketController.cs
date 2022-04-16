using System;
using System.Windows.Forms;
using travelModel;
using travelModel.validators;


namespace ProiectCS
{
    public class TicketController : Form
    {
        private ClientCtrl ctrl;
        private Flight flightForTicket;

        public TicketController(ClientCtrl ctrl, Flight flightForTicket)
        {
            this.ctrl = ctrl;
            this.flightForTicket = flightForTicket;
            InitializeComponent();
            lbCdest.Text = flightForTicket.Destination;
            lbCDeparture.Text = flightForTicket.Departure.ToString();
            lbCAirport.Text = flightForTicket.Airport;
        }

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.textBoxS = new System.Windows.Forms.TextBox();
            this.textBoxCA = new System.Windows.Forms.TextBox();
            this.textBoxTN = new System.Windows.Forms.TextBox();
            this.textBoxCN = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.buttonBuy = new System.Windows.Forms.Button();
            this.lbCAirport = new System.Windows.Forms.Label();
            this.lbCDeparture = new System.Windows.Forms.Label();
            this.lbCdest = new System.Windows.Forms.Label();
            this.lbAirport = new System.Windows.Forms.Label();
            this.lbDep = new System.Windows.Forms.Label();
            this.lbDest = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize) (this.splitContainer1)).BeginInit();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            this.SuspendLayout();
            // 
            // splitContainer1
            // 
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.Location = new System.Drawing.Point(0, 0);
            this.splitContainer1.Name = "splitContainer1";
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.textBoxS);
            this.splitContainer1.Panel1.Controls.Add(this.textBoxCA);
            this.splitContainer1.Panel1.Controls.Add(this.textBoxTN);
            this.splitContainer1.Panel1.Controls.Add(this.textBoxCN);
            this.splitContainer1.Panel1.Controls.Add(this.label6);
            this.splitContainer1.Panel1.Controls.Add(this.label5);
            this.splitContainer1.Panel1.Controls.Add(this.label4);
            this.splitContainer1.Panel1.Controls.Add(this.label3);
            this.splitContainer1.Panel1.Controls.Add(this.label1);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.Controls.Add(this.buttonBuy);
            this.splitContainer1.Panel2.Controls.Add(this.lbCAirport);
            this.splitContainer1.Panel2.Controls.Add(this.lbCDeparture);
            this.splitContainer1.Panel2.Controls.Add(this.lbCdest);
            this.splitContainer1.Panel2.Controls.Add(this.lbAirport);
            this.splitContainer1.Panel2.Controls.Add(this.lbDep);
            this.splitContainer1.Panel2.Controls.Add(this.lbDest);
            this.splitContainer1.Panel2.Controls.Add(this.label2);
            this.splitContainer1.Size = new System.Drawing.Size(760, 584);
            this.splitContainer1.SplitterDistance = 370;
            this.splitContainer1.TabIndex = 0;
            // 
            // textBoxS
            // 
            this.textBoxS.Location = new System.Drawing.Point(175, 303);
            this.textBoxS.Name = "textBoxS";
            this.textBoxS.Size = new System.Drawing.Size(176, 22);
            this.textBoxS.TabIndex = 8;
            // 
            // textBoxCA
            // 
            this.textBoxCA.Location = new System.Drawing.Point(175, 245);
            this.textBoxCA.Name = "textBoxCA";
            this.textBoxCA.Size = new System.Drawing.Size(176, 22);
            this.textBoxCA.TabIndex = 7;
            // 
            // textBoxTN
            // 
            this.textBoxTN.Location = new System.Drawing.Point(175, 180);
            this.textBoxTN.Name = "textBoxTN";
            this.textBoxTN.Size = new System.Drawing.Size(176, 22);
            this.textBoxTN.TabIndex = 6;
            // 
            // textBoxCN
            // 
            this.textBoxCN.Location = new System.Drawing.Point(175, 114);
            this.textBoxCN.Name = "textBoxCN";
            this.textBoxCN.Size = new System.Drawing.Size(176, 22);
            this.textBoxCN.TabIndex = 5;
            // 
            // label6
            // 
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.label6.Location = new System.Drawing.Point(12, 302);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(131, 23);
            this.label6.TabIndex = 4;
            this.label6.Text = "Seats";
            this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label5
            // 
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.label5.Location = new System.Drawing.Point(12, 244);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(148, 23);
            this.label5.TabIndex = 3;
            this.label5.Text = "CustomerAddress";
            this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label4
            // 
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.label4.Location = new System.Drawing.Point(12, 179);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(131, 23);
            this.label4.TabIndex = 2;
            this.label4.Text = "TouristName";
            this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label3
            // 
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.label3.Location = new System.Drawing.Point(12, 113);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(131, 23);
            this.label3.TabIndex = 1;
            this.label3.Text = "CustomerName";
            this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label1
            // 
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 13.8F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.label1.Location = new System.Drawing.Point(67, 23);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(240, 32);
            this.label1.TabIndex = 0;
            this.label1.Text = "Customer Details";
            this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // buttonBuy
            // 
            this.buttonBuy.Location = new System.Drawing.Point(229, 521);
            this.buttonBuy.Name = "buttonBuy";
            this.buttonBuy.Size = new System.Drawing.Size(121, 37);
            this.buttonBuy.TabIndex = 7;
            this.buttonBuy.Text = "BuyTicket";
            this.buttonBuy.UseVisualStyleBackColor = true;
            this.buttonBuy.Click += new System.EventHandler(this.buttonBuy_Click);
            // 
            // lbCAirport
            // 
            this.lbCAirport.Location = new System.Drawing.Point(156, 290);
            this.lbCAirport.Name = "lbCAirport";
            this.lbCAirport.Size = new System.Drawing.Size(207, 23);
            this.lbCAirport.TabIndex = 6;
            // 
            // lbCDeparture
            // 
            this.lbCDeparture.Location = new System.Drawing.Point(156, 203);
            this.lbCDeparture.Name = "lbCDeparture";
            this.lbCDeparture.Size = new System.Drawing.Size(207, 23);
            this.lbCDeparture.TabIndex = 5;
            // 
            // lbCdest
            // 
            this.lbCdest.Location = new System.Drawing.Point(156, 119);
            this.lbCdest.Name = "lbCdest";
            this.lbCdest.Size = new System.Drawing.Size(207, 23);
            this.lbCdest.TabIndex = 4;
            // 
            // lbAirport
            // 
            this.lbAirport.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.lbAirport.Location = new System.Drawing.Point(30, 291);
            this.lbAirport.Name = "lbAirport";
            this.lbAirport.Size = new System.Drawing.Size(82, 22);
            this.lbAirport.TabIndex = 3;
            this.lbAirport.Text = "Airport";
            this.lbAirport.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // lbDep
            // 
            this.lbDep.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.lbDep.Location = new System.Drawing.Point(30, 203);
            this.lbDep.Name = "lbDep";
            this.lbDep.Size = new System.Drawing.Size(100, 23);
            this.lbDep.TabIndex = 2;
            this.lbDep.Text = "Departure";
            this.lbDep.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // lbDest
            // 
            this.lbDest.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.lbDest.Location = new System.Drawing.Point(30, 117);
            this.lbDest.Name = "lbDest";
            this.lbDest.Size = new System.Drawing.Size(100, 23);
            this.lbDest.TabIndex = 1;
            this.lbDest.Text = "Destination";
            this.lbDest.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label2
            // 
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.label2.Location = new System.Drawing.Point(88, 28);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(216, 27);
            this.label2.TabIndex = 0;
            this.label2.Text = "Flight Details";
            this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // TicketController
            // 
            this.ClientSize = new System.Drawing.Size(760, 584);
            this.Controls.Add(this.splitContainer1);
            this.Name = "TicketController";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel1.PerformLayout();
            this.splitContainer1.Panel2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize) (this.splitContainer1)).EndInit();
            this.splitContainer1.ResumeLayout(false);
            this.ResumeLayout(false);
        }

        private System.Windows.Forms.Label lbCdest;
        private System.Windows.Forms.Label lbCDeparture;
        private System.Windows.Forms.Label lbCAirport;
        private System.Windows.Forms.Button buttonBuy;

        private System.Windows.Forms.Label lbAirport;

        private System.Windows.Forms.Label lbDep;

        private System.Windows.Forms.TextBox textBoxCN;
        private System.Windows.Forms.TextBox textBoxTN;
        private System.Windows.Forms.TextBox textBoxCA;
        private System.Windows.Forms.TextBox textBoxS;
        private System.Windows.Forms.Label lbDest;

        private System.Windows.Forms.Label label6;

        private System.Windows.Forms.Label label5;

        private System.Windows.Forms.Label label4;

        private System.Windows.Forms.Label label3;

        private System.Windows.Forms.Label label2;

        private System.Windows.Forms.SplitContainer splitContainer1;
        private System.Windows.Forms.Label label1;

        private void buttonBuy_Click(object sender, EventArgs e)
        {
            string customerName = textBoxCN.Text;
            string touristName = textBoxTN.Text;
            string customerAddress = textBoxCA.Text;
            try
            {
                int seats = Int32.Parse(textBoxS.Text);
                Ticket ticket = new Ticket(customerName, touristName, customerAddress, seats, flightForTicket);
                ctrl.addTicket(ticket);
                MessageBox.Show("The ticket has been bought succesfully !");
                this.Close();
                
            }
            catch (ValidationException validationException)
            {
                MessageBox.Show(validationException.Message);
            }
            catch (Exception validationException)
            {
                MessageBox.Show(validationException.Message);
            }
        }
    }
}