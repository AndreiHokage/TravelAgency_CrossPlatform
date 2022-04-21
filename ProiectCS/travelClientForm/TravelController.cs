using System;
using System.Collections.Generic;
using System.Windows.Forms;
using travelModel;
using travelServices;


namespace ProiectCS
{
    public partial class TravelController : Form
    {
        public delegate void UpdateControlEvent(Ticket ticket);
        private ClientCtrl ctrl;
        private string destinationField = null;
        private DateTime departureField;
        
        public TravelController(ClientCtrl ctrl)
        {
            this.ctrl = ctrl;
            ctrl.updateEvent += userUpdate;
            InitializeComponent();
            this.FormClosing += Form1_Closing;
            IEnumerable<Flight> flights = ctrl.filterFlightByAvailableSeats();
            foreach (var flight in flights)
                gridViewFlights.Rows.Add(flight.ID, flight.Destination, flight.Departure, flight.Airport, flight.AvailableSeats);
        }

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.gridViewFlights = new System.Windows.Forms.DataGridView();
            this.Id = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Destination = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Departure = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Airport = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.AvailableSeats = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.textBosDest = new System.Windows.Forms.TextBox();
            this.dateTimePick = new System.Windows.Forms.DateTimePicker();
            this.buttonSearch = new System.Windows.Forms.Button();
            this.dataGridViewFlightTicket = new System.Windows.Forms.DataGridView();
            this.IDT = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.DestinationT = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.DepartureT = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.AirportT = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.AvailableSeatsT = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.buttonBuyTicket = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize) (this.gridViewFlights)).BeginInit();
            ((System.ComponentModel.ISupportInitialize) (this.dataGridViewFlightTicket)).BeginInit();
            this.SuspendLayout();
            // 
            // gridViewFlights
            // 
            this.gridViewFlights.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.gridViewFlights.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {this.Id, this.Destination, this.Departure, this.Airport, this.AvailableSeats});
            this.gridViewFlights.Location = new System.Drawing.Point(12, 29);
            this.gridViewFlights.Name = "gridViewFlights";
            this.gridViewFlights.RowTemplate.Height = 24;
            this.gridViewFlights.Size = new System.Drawing.Size(605, 236);
            this.gridViewFlights.TabIndex = 0;
            // 
            // Id
            // 
            this.Id.HeaderText = "Id";
            this.Id.Name = "Id";
            this.Id.Visible = false;
            // 
            // Destination
            // 
            this.Destination.HeaderText = "Destination";
            this.Destination.Name = "Destination";
            this.Destination.Width = 200;
            // 
            // Departure
            // 
            this.Departure.HeaderText = "Departure";
            this.Departure.Name = "Departure";
            this.Departure.Width = 150;
            // 
            // Airport
            // 
            this.Airport.HeaderText = "Airport";
            this.Airport.Name = "Airport";
            this.Airport.Width = 150;
            // 
            // AvailableSeats
            // 
            this.AvailableSeats.HeaderText = "AvailableSeats";
            this.AvailableSeats.Name = "AvailableSeats";
            this.AvailableSeats.Width = 120;
            // 
            // label1
            // 
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.label1.Location = new System.Drawing.Point(646, 65);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(100, 23);
            this.label1.TabIndex = 1;
            this.label1.Text = "Destination";
            // 
            // label2
            // 
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.label2.Location = new System.Drawing.Point(646, 133);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(100, 23);
            this.label2.TabIndex = 2;
            this.label2.Text = "Departure";
            // 
            // textBosDest
            // 
            this.textBosDest.Location = new System.Drawing.Point(766, 65);
            this.textBosDest.Name = "textBosDest";
            this.textBosDest.Size = new System.Drawing.Size(174, 22);
            this.textBosDest.TabIndex = 3;
            // 
            // dateTimePick
            // 
            this.dateTimePick.Location = new System.Drawing.Point(766, 131);
            this.dateTimePick.Name = "dateTimePick";
            this.dateTimePick.Size = new System.Drawing.Size(174, 22);
            this.dateTimePick.TabIndex = 4;
            // 
            // buttonSearch
            // 
            this.buttonSearch.Location = new System.Drawing.Point(835, 192);
            this.buttonSearch.Name = "buttonSearch";
            this.buttonSearch.Size = new System.Drawing.Size(105, 36);
            this.buttonSearch.TabIndex = 5;
            this.buttonSearch.Text = "Search";
            this.buttonSearch.UseVisualStyleBackColor = true;
            this.buttonSearch.Click += new System.EventHandler(this.buttonSearch_Click);
            // 
            // dataGridViewFlightTicket
            // 
            this.dataGridViewFlightTicket.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewFlightTicket.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {this.IDT, this.DestinationT, this.DepartureT, this.AirportT, this.AvailableSeatsT});
            this.dataGridViewFlightTicket.Location = new System.Drawing.Point(12, 330);
            this.dataGridViewFlightTicket.Name = "dataGridViewFlightTicket";
            this.dataGridViewFlightTicket.RowTemplate.Height = 24;
            this.dataGridViewFlightTicket.Size = new System.Drawing.Size(605, 207);
            this.dataGridViewFlightTicket.TabIndex = 6;
            // 
            // IDT
            // 
            this.IDT.HeaderText = "ID";
            this.IDT.Name = "IDT";
            this.IDT.Visible = false;
            // 
            // DestinationT
            // 
            this.DestinationT.HeaderText = "Destination";
            this.DestinationT.Name = "DestinationT";
            this.DestinationT.Width = 200;
            // 
            // DepartureT
            // 
            this.DepartureT.HeaderText = "Departure";
            this.DepartureT.Name = "DepartureT";
            this.DepartureT.Width = 150;
            // 
            // AirportT
            // 
            this.AirportT.HeaderText = "Airport";
            this.AirportT.Name = "AirportT";
            this.AirportT.Width = 150;
            // 
            // AvailableSeatsT
            // 
            this.AvailableSeatsT.HeaderText = "AvailableSeats";
            this.AvailableSeatsT.Name = "AvailableSeatsT";
            this.AvailableSeatsT.Width = 120;
            // 
            // buttonBuyTicket
            // 
            this.buttonBuyTicket.Location = new System.Drawing.Point(835, 400);
            this.buttonBuyTicket.Name = "buttonBuyTicket";
            this.buttonBuyTicket.Size = new System.Drawing.Size(105, 34);
            this.buttonBuyTicket.TabIndex = 7;
            this.buttonBuyTicket.Text = "Buy Ticket";
            this.buttonBuyTicket.UseVisualStyleBackColor = true;
            this.buttonBuyTicket.Click += new System.EventHandler(this.buttonBuyTicket_Click);
            // 
            // TravelController
            // 
            this.BackColor = System.Drawing.SystemColors.Control;
            this.ClientSize = new System.Drawing.Size(976, 566);
            this.Controls.Add(this.buttonBuyTicket);
            this.Controls.Add(this.dataGridViewFlightTicket);
            this.Controls.Add(this.buttonSearch);
            this.Controls.Add(this.dateTimePick);
            this.Controls.Add(this.textBosDest);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.gridViewFlights);
            this.Location = new System.Drawing.Point(15, 15);
            this.Name = "TravelController";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            ((System.ComponentModel.ISupportInitialize) (this.gridViewFlights)).EndInit();
            ((System.ComponentModel.ISupportInitialize) (this.dataGridViewFlightTicket)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();
        }

        private System.Windows.Forms.Button buttonBuyTicket;

        private System.Windows.Forms.DataGridViewTextBoxColumn IDT;
        private System.Windows.Forms.DataGridViewTextBoxColumn DestinationT;
        private System.Windows.Forms.DataGridViewTextBoxColumn DepartureT;
        private System.Windows.Forms.DataGridViewTextBoxColumn AirportT;
        private System.Windows.Forms.DataGridViewTextBoxColumn AvailableSeatsT;

        private System.Windows.Forms.DataGridView dataGridViewFlightTicket;

        private System.Windows.Forms.DateTimePicker dateTimePick;
        private System.Windows.Forms.Button buttonSearch;

        private System.Windows.Forms.TextBox textBosDest;

        private System.Windows.Forms.Label label2;

        private System.Windows.Forms.Label label1;

        private System.Windows.Forms.DataGridViewTextBoxColumn Id;
        private System.Windows.Forms.DataGridViewTextBoxColumn Destination;
        private System.Windows.Forms.DataGridViewTextBoxColumn Departure;
        private System.Windows.Forms.DataGridViewTextBoxColumn Airport;
        private System.Windows.Forms.DataGridViewTextBoxColumn AvailableSeats;

        private System.Windows.Forms.DataGridView gridViewFlights;

        private void buttonSearch_Click(object sender, EventArgs e)
        {
            dataGridViewFlightTicket.Rows.Clear();
            destinationField = textBosDest.Text;
            departureField = dateTimePick.Value;
            if(destinationField == null || departureField == null)
                return;
            try
            {
                IEnumerable<Flight> flights =
                    ctrl.filterFlightsByDestinationAndDeparture(destinationField, departureField);
                foreach (var flight in flights)
                    dataGridViewFlightTicket.Rows.Add(flight.ID, flight.Destination, flight.Departure, flight.Airport,
                        flight.AvailableSeats);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void buttonBuyTicket_Click(object sender, EventArgs e)
        {
            DataGridViewRow selectedRow = dataGridViewFlightTicket.CurrentRow;
            if(selectedRow == null)
                return;
            try
            {
                int Id = Int32.Parse(selectedRow.Cells[0].Value.ToString());
                string destination = selectedRow.Cells[1].Value.ToString();
                DateTime departure = Convert.ToDateTime(selectedRow.Cells[2].Value.ToString());
                string airport = selectedRow.Cells[3].Value.ToString();
                int availableSeats = Int32.Parse(selectedRow.Cells[4].Value.ToString());
                Flight flightForTicket = new Flight(Id, destination, departure, airport, availableSeats);

                TicketController ticketController = new TicketController(ctrl, flightForTicket);
                ticketController.Show();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void Form1_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            ctrl.logout();
            Console.WriteLine("Application closing");
            ctrl.updateEvent -= userUpdate;
        }

        public void userUpdate(object sender, TravelUserEventArgs e)
        {
            if (e.TravelEventType == TravelUserEvent.BuyTicket)
            {
                Ticket ticket = (Ticket) e.Data;
                this.BeginInvoke(new UpdateControlEvent(updateModelTables), new Object[]{ticket});
                Console.WriteLine("[TravelWindow] add ticket");
            }
        }

        private void updateModelTables(Ticket ticket)
        {
            DateTime dateTimeDateField = ticket.FlightForTicket.Departure.Date;
            DateTime dateTimeDateTicket = ticket.FlightForTicket.Departure.Date;

            if (destinationField == ticket.FlightForTicket.Destination &&
                dateTimeDateField == dateTimeDateTicket)
            {
                try
                {
                    IEnumerable<Flight> flightTicket = ctrl.filterFlightsByDestinationAndDeparture(destinationField, departureField);
                    dataGridViewFlightTicket.Rows.Clear();
                    foreach (var flight in flightTicket)
                        dataGridViewFlightTicket.Rows.Add(flight.ID, flight.Destination,
                            flight.Departure, flight.Airport, flight.AvailableSeats);
                }
                catch (TravelException ex)
                {
                    MessageBox.Show(ex.Message);
                }
            }

            try
            {
                IEnumerable<Flight> flights = ctrl.filterFlightByAvailableSeats();
                gridViewFlights.Rows.Clear();
                foreach (var flight in flights)
                    gridViewFlights.Rows.Add(flight.ID, flight.Destination,
                        flight.Departure, flight.Airport, flight.AvailableSeats);
            }
            catch (TravelException ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}