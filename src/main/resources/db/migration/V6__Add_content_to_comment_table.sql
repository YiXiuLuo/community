alter table COMMENT
	add content varchar(1024);

comment on column COMMENT.content is '回复内容';