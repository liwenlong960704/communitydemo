create table comment
(
	id bigint auto_increment,
	parent_id bigint not null comment '问题id',
	type int not null comment '父类类型',
	commentator int not null comment '评论人',
	gmt_create bigint not null,
	gmt_modified bigint not null,
	like_count bigint default 0 null,
	constraint comment_pk
		primary key (id)
);