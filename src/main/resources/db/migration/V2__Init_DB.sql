insert into friend_service.friendship (id, status_id, src_person_id, dst_person_id)
values(1, 1, 1, 1);
insert into friend_service.friendship (id, status_id, src_person_id, dst_person_id)
values(2, 2, 2, 2);
insert into friend_service.friendship (id, status_id, src_person_id, dst_person_id)
values(3, 3, 3, 3);
insert into friend_service.friendship (id, status_id, src_person_id, dst_person_id)
values(4, 4, 4, 4);
insert into friend_service.friendship (id, status_id, src_person_id, dst_person_id)
values(5, 5, 5, 5);

insert into friend_service.friendship_status (id, time, name, friendship_status)
values(1, '2000-01-01', 'Gandalf', 'REQUEST');
insert into friend_service.friendship_status (id, time, name, friendship_status)
values(2, '2000-02-02', 'Frodo', 'FRIEND');
insert into friend_service.friendship_status (id, time, name, friendship_status)
values(3, '2000-03-03', 'Aragorn', 'BLOCKED');
insert into friend_service.friendship_status (id, time, name, friendship_status)
values(4, '2000-04-04', 'Aragorn', 'DECLINED');
insert into friend_service.friendship_status (id, time, name, friendship_status)
values(5, '2000-05-05', 'Sauron', 'SUBSCRIBED');