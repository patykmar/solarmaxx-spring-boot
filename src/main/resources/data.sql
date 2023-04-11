insert into `users` (`id`, `email`, `roles`, `password`) values
(1000, 'admin@fake.com', 'admin', 'st30ngPa$$worD!'),
(1001, 'user@fake.com', 'user', 'st30ngPa$$worD');

insert into `tariff` (`id`, `user_id`, `name`, `price`, `available_from`, `available_to`) values
(1001, 1000, 'High', 123456, '2022-11-12', '2022-11-15'),
(1002, 1001, 'High', 123456, '2022-11-12', '2022-11-15'),
(1003, 1000, 'Low', 1234, '2022-01-01', '2022-12-31'),
(1004, 1001, 'Log', 1234, '2022-01-01', '2022-12-31');

insert into `relay_type` (`id`, `name`, `url_status`, `url_toggle`, `turn_on`, `turn_off`, `device_type_string`) values
(1001, 'Tasmota', 'http://%IP:%PORT/cm?cmnd=Power%ID%20STATUS', 'http://%IP:%PORT/cm?cmnd=Power%ID%20%TOGGLE', 'ON', 'OFF', 'tasmota'),
(1002, 'Shelly Pro', 'http://%IP:%PORT/rpc/Switch.GetStatus?id=%ID', 'http://%IP:%PORT/rpc/Switch.Toggle?id=%ID', 'ON', 'OFF', 'shelly-pro');

insert into `relay` (`id`, `name`, `ip_address`, `port`, `output_count`, `user_id`, `device_type_id`) values
(1001, 'Tasmota, 2 toggle', '192.168.22.229', 80, 2, 1000, 1001),
(1002, 'Tasmota, Sonoff Basic', '192.168.22.232', 80, 1, 1000, 1001),
(1003, 'bojler1tasmota, Shelly 1', '192.168.22.233', 80, 1, 1000, 1001),
(1004, 'Shelly 4PM', '192.168.22.234', 80, 4, 1000, 1002);

--insert into `relay_schedule` (`id`, `relay_id`, output_id, `on_start`, `on_end`, `day_number`) values
--(1001, 1001, 1, '07:36', '18:36', 1),
--(1002, 1001, 1, '07:36', '18:36', 3),
--(1003, 1002, 1, '07:36', '18:36', 3),
--(1004, 1002, 1, '07:36', '18:36', 4),
--(1005, 1003, 1, '07:36', '18:36', 3);

insert into `relay_output` (`id`, `description`, `output_id`,`output_status`, `relay_id`) values
(1001, 'Relay port 1001', 1, 'ON',   1001 ),
(1002, 'Relay port 1002', 2, 'OFF',  1001 ),
(1003, 'Relay port 1003', 1, 'OFF',  1002 ),
(1004, 'Relay port 1004', 1, 'ON',   1003 ),
(1005, 'Relay port 1005', 0, 'ON',   1004 ),
(1006, 'Relay port 1006', 1, 'NA',   1004 ),
(1007, 'Relay port 1007', 2, 'NA',   1004 ),
(1008, 'Relay port 1008', 3, 'NA',   1004 );

insert into `relay_output_schedule` (`id`, `relay_output_id`, `on_start`, `on_end`, `day_number`) values
(1001, 1001, '07:36', '18:36', 1),
(1002, 1001, '07:36', '18:36', 3),
(1003, 1002, '07:36', '18:36', 3),
(1004, 1002, '07:36', '18:36', 4),
(1005, 1003, '07:36', '18:36', 3);