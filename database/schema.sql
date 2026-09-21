DROP DATABASE IF EXISTS hospital_booking;
CREATE DATABASE hospital_booking CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hospital_booking;

CREATE TABLE departments(
  department_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(80) NOT NULL UNIQUE,
  description VARCHAR(255) NOT NULL,
  location VARCHAR(80) NOT NULL,
  phone VARCHAR(20) NOT NULL
);
CREATE TABLE users(
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  full_name VARCHAR(120) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  password_hash CHAR(64) NOT NULL,
  role ENUM('PATIENT','DOCTOR','ADMIN','SYSTEM_ADMIN') NOT NULL DEFAULT 'PATIENT',
  phone VARCHAR(20), address VARCHAR(255), is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_users_name(full_name), INDEX idx_users_role(role)
);
CREATE TABLE doctors(
  user_id INT PRIMARY KEY,
  department_id INT NOT NULL,
  specialization VARCHAR(120) NOT NULL,
  qualification VARCHAR(150) NOT NULL,
  room_number VARCHAR(20) NOT NULL,
  FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE,
  FOREIGN KEY(department_id) REFERENCES departments(department_id)
);
CREATE TABLE appointments(
  appointment_id INT AUTO_INCREMENT PRIMARY KEY,
  patient_id INT NOT NULL, doctor_id INT NOT NULL,
  appointment_date DATE NOT NULL, appointment_time TIME NOT NULL,
  status ENUM('PENDING','CONFIRMED','COMPLETED','CANCELLED') NOT NULL DEFAULT 'PENDING',
  reason VARCHAR(500) NOT NULL, cancellation_reason VARCHAR(500),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY(patient_id) REFERENCES users(user_id),
  FOREIGN KEY(doctor_id) REFERENCES users(user_id),
  UNIQUE KEY uq_doctor_slot(doctor_id,appointment_date,appointment_time),
  INDEX idx_appointment_date(appointment_date), INDEX idx_appointment_status(status),
  INDEX idx_appointment_patient(patient_id), INDEX idx_appointment_doctor(doctor_id)
);
CREATE TABLE symptom_checks(
  check_id INT AUTO_INCREMENT PRIMARY KEY, patient_id INT NOT NULL,
  symptoms VARCHAR(1000) NOT NULL, severity ENUM('MILD','MODERATE','SEVERE') NOT NULL,
  recommendation VARCHAR(500) NOT NULL, checked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY(patient_id) REFERENCES users(user_id) ON DELETE CASCADE,
  INDEX idx_symptom_patient(patient_id)
);

INSERT INTO departments(name,description,location,phone) VALUES
('General Medicine','Primary care and general consultations','Main Building, Ground Floor','021 555 0101'),
('Cardiology','Heart and cardiovascular care','East Wing, Floor 2','021 555 0102'),
('Paediatrics','Medical care for infants and children','Family Wing, Floor 1','021 555 0103'),
('Dermatology','Skin, hair and nail treatment','West Wing, Floor 1','021 555 0104'),
('Orthopaedics','Bone, joint and muscle care','Rehabilitation Wing','021 555 0105');

-- All demonstration accounts use Password1. MySQL hashes it with SHA-256.
INSERT INTO users(full_name,email,password_hash,role,phone,address) VALUES
('Lerato Mokoena','patient@ubuntuhealth.co.za',SHA2('Password1',256),'PATIENT','072 410 2210','12 Long Street, Cape Town'),
('Sipho Dlamini','sipho.dlamini@example.co.za',SHA2('Password1',256),'PATIENT','073 610 3321','44 Main Road, Observatory'),
('Ayesha Khan','ayesha.khan@example.co.za',SHA2('Password1',256),'PATIENT','074 720 4432','8 Protea Avenue, Rondebosch'),
('Thando Jacobs','thando.jacobs@example.co.za',SHA2('Password1',256),'PATIENT','076 830 5543','25 Beach Road, Sea Point'),
('Nomsa Ndlovu','nomsa.ndlovu@example.co.za',SHA2('Password1',256),'PATIENT','078 940 6654','7 Station Road, Bellville'),
('Dr Naledi Molefe','doctor@ubuntuhealth.co.za',SHA2('Password1',256),'DOCTOR','021 555 1101','Ubuntu Health Campus'),
('Dr Michael Petersen','m.petersen@ubuntuhealth.co.za',SHA2('Password1',256),'DOCTOR','021 555 1102','Ubuntu Health Campus'),
('Dr Zanele Mbatha','z.mbatha@ubuntuhealth.co.za',SHA2('Password1',256),'DOCTOR','021 555 1103','Ubuntu Health Campus'),
('Dr Imraan Davids','i.davids@ubuntuhealth.co.za',SHA2('Password1',256),'DOCTOR','021 555 1104','Ubuntu Health Campus'),
('Dr Rachel Adams','r.adams@ubuntuhealth.co.za',SHA2('Password1',256),'DOCTOR','021 555 1105','Ubuntu Health Campus'),
('Nandi Williams','admin@ubuntuhealth.co.za',SHA2('Password1',256),'ADMIN','021 555 1201','Ubuntu Health Campus'),
('Themba Maseko','thembam@ubuntuhealth.co.za',SHA2('Password1',256),'ADMIN','021 555 1202','Ubuntu Health Campus'),
('System Administrator','sysadmin@ubuntuhealth.co.za',SHA2('Password1',256),'SYSTEM_ADMIN','021 555 1301','Ubuntu Health Campus'),
('Kea Modise','kea.modise@example.co.za',SHA2('Password1',256),'PATIENT','071 242 9970','18 Loop Street, Cape Town'),
('Jason Naidoo','jason.naidoo@example.co.za',SHA2('Password1',256),'PATIENT','079 332 1164','91 Durban Road, Mowbray');

INSERT INTO doctors(user_id,department_id,specialization,qualification,room_number) VALUES
(6,1,'General Practitioner','MBChB, University of Cape Town','G12'),
(7,2,'Cardiologist','MBChB, FCP(SA), Cert Cardiology','E204'),
(8,3,'Paediatrician','MBChB, FCPaed(SA)','F105'),
(9,4,'Dermatologist','MBChB, FC Derm(SA)','W116'),
(10,5,'Orthopaedic Surgeon','MBChB, FC Orth(SA)','R08');

INSERT INTO appointments(patient_id,doctor_id,appointment_date,appointment_time,status,reason,cancellation_reason) VALUES
(1,6,CURDATE()+INTERVAL 3 DAY,'09:00','PENDING','Persistent headaches',NULL),
(2,7,CURDATE()+INTERVAL 4 DAY,'10:00','CONFIRMED','Follow-up heart assessment',NULL),
(3,8,CURDATE()+INTERVAL 5 DAY,'11:30','PENDING','Child vaccination consultation',NULL),
(4,9,CURDATE()+INTERVAL 6 DAY,'13:00','CONFIRMED','Recurring skin irritation',NULL),
(5,10,CURDATE()+INTERVAL 7 DAY,'14:30','PENDING','Knee pain after exercise',NULL),
(14,6,CURDATE()+INTERVAL 8 DAY,'08:30','PENDING','Annual wellness check',NULL),
(15,7,CURDATE()+INTERVAL 9 DAY,'12:00','CONFIRMED','Blood pressure review',NULL),
(1,8,CURDATE()-INTERVAL 14 DAY,'10:30','COMPLETED','Paediatric family consultation',NULL),
(2,9,CURDATE()-INTERVAL 10 DAY,'09:30','COMPLETED','Allergy assessment',NULL),
(3,10,CURDATE()-INTERVAL 4 DAY,'15:00','CANCELLED','Ankle injury review','Patient recovered before appointment');
