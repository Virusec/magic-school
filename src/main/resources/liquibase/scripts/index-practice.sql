-- liquibase formatted sql

-- changeset ashikin:1
CREATE INDEX student_name_index ON student (name)

-- changeset ashikin:2
CREATE INDEX faculty_nc_idx ON faculty (name, color)