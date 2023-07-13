INSERT INTO `irrigation-service`.error_message(error_type, error_message)
VALUES('InvalidSlotTimingException', 'Slots startTime values must be less than endTime values');

INSERT INTO `irrigation-service`.error_message(error_type, error_message)
VALUES('OverlappingSlotsException', 'Two irrigation time slots should not overlap');