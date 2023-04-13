CRATE DATABASE IF NOT EXISTS 'board_study'

CREATE TABLE 'board'(
	'id' NUMBER(20) PRIMARY KEY,
	'title' varchar2(200) NOT NULL,
	'content' text NOT NULL,
	'read_cnt' NUMBER(11) NOT NULL DEFAULT 0,
	'register_id' VARCHAR(100) NOT NULL,
	'register_time' DATETIME NULL DEFAULT NULL,
	'update_time' DATETIME NULL DEFAULT NULL
);