CREATE TABLE resume
(
    uuid      CHAR(36) NOT NULL PRIMARY KEY,
    full_name TEXT     NOT NULL
);
CREATE TABLE contact
(
    id          SERIAL PRIMARY KEY,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL,
    resume_uuid CHAR(36) NOT NULL REFERENCES resume (uuid) ON UPDATE RESTRICT ON DELETE CASCADE
);
CREATE UNIQUE INDEX contact_uuid_type_index
    ON contact (resume_uuid, type);

CREATE TABLE section
(
    id          SERIAL PRIMARY KEY,
    type        TEXT NOT NULL,
    value       TEXT NOT NULL,
    resume_uuid CHAR(36) REFERENCES resume (uuid) ON UPDATE RESTRICT ON DELETE CASCADE
);
CREATE UNIQUE INDEX section_idx ON section (resume_uuid, type);