insert into `users` (`id`, `email`, `roles`, `password`) VALUES
(1, 'admin@fake.com', 'admin', 'st30ngPa$$worD!'),
(2, 'admin@fake.com', 'user', 'st30ngPa$$worD');

insert into `tariff` (`id`, `user_id`, `name`, `price`, `available_from`, `available_to`) VALUES
(1, 1, 'High', 123456, '2022-11-12', '2022-11-15'),
(2, 2, 'High', 123456, '2022-11-12', '2022-11-15'),
(3, 1, 'Low', 1234, '2022-01-01', '2022-12-31'),
(4, 2, 'Log', 1234, '2022-01-01', '2022-12-31');

insert into `relay_type` (`id`, `name`, `url_status`, `url_toggle`, `turn_on`, `turn_off`, `device_type_string`) VALUES
(1, 'Tasmota', 'http://%IP:%PORT/cm?cmnd=Power%ID%20STATUS', 'http://%IP:%PORT/cm?cmnd=Power%ID%20%TOGGLE', 'ON', 'OFF', 'tasmota'),
(2, 'Shelly Pro', 'http://%IP:%PORT/rpc/Switch.GetStatus?id=%ID', 'http://%IP:%PORT/rpc/Switch.Toggle?id=%ID', 'ON', 'OFF', 'shelly-pro');

insert into `relay` (`id`, `name`, `ip_address`, `port`, `output_count`, `user_id`, `device_type_id`) VALUES
(1, 'Tasmota, 2 toggle', '192.168.22.229', 80, 2, 1, 1),
(2, 'Tasmota, Sonoff Basic', '192.168.22.232', 80, 1, 1, 1),
(3, 'bojler1tasmota, Shelly 1', '192.168.22.233', 80, 1, 1, 1),
(4, 'Shelly 4PM', '192.168.22.233', 80, 4, 1, 2);

insert into `relay_schedule` (`id`, `relay_id`, `on_start`, `on_end`, `day_number`) VALUES
(1, 1, '07:36:43', '18:36:43', 1),
(2, 1, '07:36:43', '18:36:43', 3),
(3, 2, '07:36:43', '18:36:43', 3),
(4, 2, '07:36:43', '18:36:43', 0),
(5, 3, '07:36:43', '18:36:43', 3);