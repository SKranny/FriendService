insert into friend_service.friendship (status_id, src_person_id, dst_person_id)
values (1, 1, 1),
       (2, 2, 2),
       (3, 3, 3),
       (4, 4, 4),
       (5, 5, 5);

insert into friend_service.friendship_status (time, name, friendship_status)
values ('2000-01-01', 'Gandalf', 'REQUEST'),
       ('2000-02-02', 'Frodo', 'FRIEND'),
       ('2000-03-03', 'Aragorn', 'BLOCKED'),
       ('2000-04-04', 'Sam', 'DECLINED'),
       ('2000-05-05', 'Sauron', 'SUBSCRIBED');