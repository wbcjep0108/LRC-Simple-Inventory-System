# LRC-Simple-Inventory-System

This is a simple Java Swing application for managing book records. It connects to a PostgreSQL database (`Book_db`) and allows users to add new books with their details.

---

## Features

- **Add Book Details**  
  Users can enter the following information for each book:
  - **Title** (varchar, max 100 characters)
  - **Author** (varchar, max 100 characters)
  - **Description** (varchar, max 500 characters)
  - **Price** (double)
  - **Book ID** (int, primary key)
  - **Availability** (Available / Not Available via radio buttons)

- **Buttons**
  - **Save**: Save the book information to the database
  - **Reset**: Clear all input fields
  - **Exit**: Close the application

---

## How to Use

1. Clone the repository:
   ```bash
   git clone https://github.com/wbcjep0108/LRC-Simple-Inventory-System.git