CREATE TABLE IF NOT EXISTS users (
	id_users SERIAL PRIMARY KEY,
	email VARCHAR(45) NOT NULL,
	password VARCHAR(45) NOT NULL,
	status INT NOT NULL,
	user_create VARCHAR(45),
	date_create TIMESTAMP,
	user_delete VARCHAR(45),
	date_delete TIMESTAMP,
	user_modif VARCHAR(45),
	date_modif TIMESTAMP
);

CREATE TABLE IF NOT EXISTS course (
	id_course SERIAL PRIMARY KEY,
	code VARCHAR(45) NOT NULL,
	name VARCHAR(45) NOT NULL,
	status INT NOT NULL,
	user_create VARCHAR(45),
	date_create TIMESTAMP,
	user_delete VARCHAR(45),
	date_delete TIMESTAMP,
	user_modif VARCHAR(45),
	date_modif TIMESTAMP
);

CREATE TABLE IF NOT EXISTS documents_type (
	id_documents_type SERIAL PRIMARY KEY,
	cod_type VARCHAR(45) NOT NULL,
	desc_type VARCHAR(45) NOT NULL,
	status INT NOT NULL,
	user_create VARCHAR(45),
	date_create TIMESTAMP,
	user_delete VARCHAR(45),
	date_delete TIMESTAMP,
	user_modif VARCHAR(45),
	date_modif TIMESTAMP
);

CREATE TABLE IF NOT EXISTS student (
	id_student SERIAL PRIMARY KEY,
	name VARCHAR(45) NOT NULL,
	surname VARCHAR(45) NOT NULL,
	num_document VARCHAR(8) NOT NULL,
	telephone VARCHAR(15),
	age INT NOT NULL,
	status INT NOT NULL,
	user_create VARCHAR(45),
	date_create TIMESTAMP,
	user_delete VARCHAR(45),
	date_delete TIMESTAMP,
	user_modif VARCHAR(45),
	date_modif TIMESTAMP,
	document_type_id_document_type INT NOT NULL,
	FOREIGN KEY (document_type_id_document_type) REFERENCES documents_type (id_documents_type) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS teacher (
	id_teacher SERIAL PRIMARY KEY,
	name VARCHAR(45) NOT NULL,
	surname VARCHAR(45) NOT NULL,
	num_document VARCHAR(8) NOT NULL,
	telephone VARCHAR(15),
	age INT NOT NULL,
	status INT NOT NULL,
	user_create VARCHAR(45),
	date_create TIMESTAMP,
	user_delete VARCHAR(45),
	date_delete TIMESTAMP,
	user_modif VARCHAR(45),
	date_modif TIMESTAMP,
	document_type_id_document_type INT NOT NULL,
	FOREIGN KEY (document_type_id_document_type) REFERENCES documents_type (id_documents_type) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS score (
	id_score SERIAL PRIMARY KEY,
	score VARCHAR(45) NOT NULL,
	status INT NOT NULL,
	user_create VARCHAR(45),
	date_create TIMESTAMP,
	user_delete VARCHAR(45),
	date_delete TIMESTAMP,
	user_modif VARCHAR(45),
	date_modif TIMESTAMP,
	course_id_course INT NOT NULL,
	student_id_student INT NOT NULL,
	teacher_id_teacher INT NOT NULL,
	FOREIGN KEY (course_id_course) REFERENCES course (id_course) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (student_id_student) REFERENCES student (id_student) ON DELETE NO ACTION ON UPDATE NO ACTION,
	FOREIGN KEY (teacher_id_teacher) REFERENCES teacher (id_teacher) ON DELETE NO ACTION ON UPDATE NO ACTION
);


