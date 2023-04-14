create table session
(
    id varchar(36) not null,
    title varchar(64) not null,
    primary key(id)
);

create table member
(
    id varchar(36) not null,
    session_id varchar(36) not null,
    name varchar(64) not null,
    foreign key(session_id) references session(id),
    primary key(id)
);

create table user_story
(
    id varchar(36) not null,
    session_id varchar(36) not null,
    description varchar(255) not null,
    status varchar(32) not null,
    foreign key(session_id) references session(id),
    primary key(id)
);

create table vote
(
    member_id varchar(36) not null,
    user_story_id varchar(36) not null,
    vote_value varchar(64),
    foreign key(member_id) references member(id),
    foreign key(user_story_id) references user_story(id),
    primary key(member_id, user_story_id)
);




