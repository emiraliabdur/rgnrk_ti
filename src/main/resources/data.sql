insert into session values('bfb2132c-d6c4-11ed-afa1-0242ac120002', 'Session 1');
insert into session values('c34edce0-d6c4-11ed-afa1-0242ac120002', 'Session 2');

insert into member values('f68d8e76-d6c4-11ed-afa1-0242ac120002','bfb2132c-d6c4-11ed-afa1-0242ac120002', 'Bobby');
insert into member values('00a23038-d6c5-11ed-afa1-0242ac120002','c34edce0-d6c4-11ed-afa1-0242ac120002', 'John');

insert into user_story values('19ca7cdc-d6c5-11ed-afa1-0242ac120002', 'bfb2132c-d6c4-11ed-afa1-0242ac120002', 'User Story 1', 'VOTING');
insert into user_story values('263df944-d6c5-11ed-afa1-0242ac120002', 'c34edce0-d6c4-11ed-afa1-0242ac120002', 'User Story 2', 'VOTED');

insert into vote values('f68d8e76-d6c4-11ed-afa1-0242ac120002', '19ca7cdc-d6c5-11ed-afa1-0242ac120002', 'Vote 1');
insert into vote values('00a23038-d6c5-11ed-afa1-0242ac120002', '19ca7cdc-d6c5-11ed-afa1-0242ac120002', 'Vote 2');
insert into vote values('f68d8e76-d6c4-11ed-afa1-0242ac120002', '263df944-d6c5-11ed-afa1-0242ac120002', 'Vote 3');
insert into vote values('00a23038-d6c5-11ed-afa1-0242ac120002', '263df944-d6c5-11ed-afa1-0242ac120002', 'Vote 4');




