# Bus Conductor Ticket Booking & Boarding System

This project is a small real-world style application built to help a bus conductor manage bookings and board passengers faster.

The main idea was not just to store bookings, but to actually solve a real problem that happens in buses — passengers blocking the aisle while settling into seats, which increases boarding time.

So the system not only books seats, but also decides the **best order in which passengers should enter the bus**.

---

## What the Application Does

### Booking Screen

The conductor can:

* Choose travel date
* Enter passenger mobile number
* Select seats from a 2×2 layout (15 rows)
* Book up to 6 seats per mobile number per day

The UI immediately shows booked seats and prevents double booking.
All feedback is shown using small notification messages instead of alerts.

After booking, the conductor gets a confirmation with Booking ID.

---

### Boarding Dashboard

This screen helps during actual boarding.

It shows:

* All bookings for the selected date
* The best boarding order (automatically calculated)
* Call passenger option
* Board button to mark passenger entry
* Live boarding status (Boarding / Waiting / Boarded)

While one group is settling into their seat, the next group must wait.

---

### Live Boarding Behaviour

In real buses, when a passenger enters a row, nobody behind them can cross until they settle.

So the system simulates:

* 60 seconds settling time per group
* Only one group boarding at a time
* Others automatically wait
* Estimated completion time updates live

---

## Why Ordering Matters

If people board randomly:

A1 → A7 → A15

Every passenger blocks the next one and delay keeps adding.

If they board from back to front:

A15 → A7 → A1

Nobody blocks anyone and boarding finishes much faster.

---

## How the System Decides Order

Each booking has seats in some row.
The farthest row from the entrance should enter first.

So I implemented a simple greedy approach:

1. Find the farthest seat row in each booking
2. Sort bookings from back to front
3. Board them one by one

This avoids cascading waiting time.

Time Complexity: O(n log n) because of sorting.

---

## Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Data JPA (Hibernate)
* H2 Database
* REST APIs
* Validation & Exception Handling

### Frontend

* React.js
* Axios
* Material UI Snackbar
* Basic animations

---

## How to Run

### Backend

```
cd backend
./mvnw spring-boot:run
```

Runs on:
[http://localhost:8080](http://localhost:8080)

H2 DB Console:
[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

JDBC:

```
jdbc:h2:mem:busdb
```

---

### Frontend

```
cd frontend
npm install
npm start
```

Runs on:
[http://localhost:3000](http://localhost:3000)

---

## API Endpoints

| Method | Endpoint                     | Purpose            |
| ------ | ---------------------------- | ------------------ |
| POST   | /api/bookings                | Create booking     |
| GET    | /api/bookings/{date}         | Get bookings       |
| GET    | /api/bookings/{date}/optimal | Get boarding order |
| PATCH  | /api/bookings/{id}/board     | Mark boarded       |

---

## Edge Cases Considered

* Same seat cannot be booked twice
* Max 6 seats per mobile per day
* Boarding blocked while someone settling
* Invalid mobile numbers
* Date-wise separation of bookings

---

## Assumptions

* Single front door
* Walking time ignored
* Group boards together
* Every group takes 60 seconds to settle

---

## Final Outcome

Instead of passengers blocking each other repeatedly, the conductor gets a clear boarding order and total boarding time becomes predictable and shorter.

The goal of this project was to build something closer to an operational tool rather than a simple CRUD demo.
