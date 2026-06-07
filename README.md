# MediTrack — Medical Appointment Management System

MediTrack is a backend console-driven application written in modern Java designed to streamline medical practice workflows. It manages foundational clinical entities including **Doctors**, **Patients**, and **Appointments**, enabling healthcare administrators or automated systems to create slots, search records dynamically, and process transactions securely.

---

## 🚀 Key Features

### 1. Core Profile Management (CRUD)
* **Doctor Lifecycle:** Register profiles with specific operational parameters like domain specialization, years of professional experience, and baseline consultation fees.
* **Patient Registration:** Seamless onboarding and tracking of unique patient profile identification records.

### 2. Schedule Slot Generator
* Generates empty checkup slots for specific doctor profiles using precise date-time stamps (`LocalDateTime`).
* Prevents scheduling drift or invalid assignments by asserting structural system data validations prior to allocation.

### 3. Dynamic Search & Multi-Criteria Filtering
* Combines complex search behaviors using modern Java Streams API.
* Allows ad-hoc lookups for open sessions by specifying single or grouped conditions:
  * Doctor Name components (Partial and case-insensitive matching).
  * System Specializations (e.g., `CARDIOLOGY`, `DERMATOLOGY`).
  * Minimum Years of Professional Experience.
  * Maximum Consultation Budget Cap.

### 4. Robust Transaction Processing
* **Lock & Reserve System:** Switches open `AVAILABLE` appointment slots into operational state lifecycles (`PENDING`, `CONFIRMED`, `CANCELLED`).
* **Crash-Resilient Design:** Custom defensive architecture utilizing explicit `Validator` layers to prevent system crashes during invalid payload requests or empty record lookups.

---

## 🛠️ Tech Stack & Concepts Demonstrated

* **Language:** Java 8+ (utilizing Lambda expressions, Stream API processing, and Time model packages).
* **Architecture:** Decoupled Service-Repository structural paradigm pattern.
* **Date Handling:** Standardized parsing patterns via `java.time.LocalDateTime` and `DateTimeFormatter`.
* **State Management:** Strict lifecycle control using domain-specific Java Enums (`AppointmentStatus`, `Specialization`).

---

## 📂 Structural Class Overview

The system is organized into decoupled layers to maintain a clean separation of concerns:

* **`com.airtribe.meditrack.entity`**: Domain objects holding data schemas (`Doctor`, `Patient`, `Appointment`).
* **`com.airtribe.meditrack.enums`**: System control constants managing specialized medical domains and reservation lifecycles.
* **`com.airtribe.meditrack.repository`**: In-memory data collections isolating low-level mutation logic.
* **`com.airtribe.meditrack.service`**: 
  * `DoctorService` & `PatientService`: Handles validations and orchestrates base domain access.
  * `AppointmentManagerService`: Processes the core business transactions, searching pipelines, scheduling slots, and safe status transitions.
* **`com.airtribe.meditrack.util`**: Centralized exception handling blocks and parameter objects (`DoctorSearchCriteria`, `Validator`).

---

## ⚙️ Compilation and Manual Execution

Since this project avoids external framework dependencies for its core logic, you can easily compile and run it straight from any standard terminal.

### 1. Compile Source Files
Navigate to the root project directory and compile all Java modules into a binary destination directory (`bin`):
```bash
javac -d bin src/com/airtribe/meditrack/ui/Main.java src/com/airtribe/meditrack/test/TestRunner.java