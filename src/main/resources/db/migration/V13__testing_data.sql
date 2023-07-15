INSERT INTO plot (id, code, area, crop_type) VALUES(16, 'PLOT-SUCCESS-1', 'test', 'peach');
INSERT INTO plot (id, code, area, crop_type) VALUES(17, 'PLOT-SUCCESS-2', 'test', 'apple');
INSERT INTO plot (id, code, area, crop_type) VALUES(18, 'PLOT-SUCCESS-3', 'test', 'bananas');
INSERT INTO plot (id, code, area, crop_type) VALUES(19, 'PLOT-SUCCESS-4', 'test', 'peach');
INSERT INTO plot (id, code, area, crop_type) VALUES(20, 'PLOT-ERROR-1', 'test', 'bananas');
INSERT INTO plot (id, code, area, crop_type) VALUES(21, 'PLOT-ERROR-2', 'test', 'mango');
INSERT INTO plot (id, code, area, crop_type) VALUES(22, 'PLOT-ERROR-3', 'test', 'peach');

INSERT INTO plot_irrigation_slot (amount_liters, start_time, end_time, plot_id, status) VALUES(150, '16:00:00', '16:30:00', 16, 'READY');
INSERT INTO plot_irrigation_slot (amount_liters, start_time, end_time, plot_id, status) VALUES(88, '16:35:00', '16:40:00', 17, 'READY');
INSERT INTO plot_irrigation_slot (amount_liters, start_time, end_time, plot_id, status) VALUES(150, '16:00:00', '16:30:00', 18, 'READY');
INSERT INTO plot_irrigation_slot (amount_liters, start_time, end_time, plot_id, status) VALUES(888, '16:40:00', '17:00:00', 19, 'READY');
INSERT INTO plot_irrigation_slot (amount_liters, start_time, end_time, plot_id, status) VALUES(11, '16:40:00', '17:00:00', 20, 'READY');
INSERT INTO plot_irrigation_slot (amount_liters, start_time, end_time, plot_id, status) VALUES(120, '16:40:00', '17:00:00', 21, 'READY');
INSERT INTO plot_irrigation_slot (amount_liters, start_time, end_time, plot_id, status) VALUES(88, '16:40:00', '17:00:00', 22, 'READY');