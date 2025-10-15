-- Insert sample users with different roles
INSERT INTO users (username, password, email, full_name, phone_number, address, city, state, zip_code, role, enabled, created_at) VALUES
('admin', '$2a$10$DowJonesRocks123456789O.u7cX/QlNjN9vQEpgH9fJcHXqrH2i', 'admin@sara.com', 'System Administrator', '+1234567890', '123 Admin Street', 'Washington', 'DC', '20001', 'ADMIN', true, CURRENT_TIMESTAMP),
('donor1', '$2a$10$DowJonesRocks123456789O.u7cX/QlNjN9vQEpgH9fJcHXqrH2i', 'donor1@sara.com', 'John Donor', '+1234567891', '456 Generous Ave', 'New York', 'NY', '10001', 'DONOR', true, CURRENT_TIMESTAMP),
('volunteer1', '$2a$10$DowJonesRocks123456789O.u7cX/QlNjN9vQEpgH9fJcHXqrH2i', 'volunteer1@sara.com', 'Jane Volunteer', '+1234567892', '789 Helper Street', 'Los Angeles', 'CA', '90001', 'VOLUNTEER', true, CURRENT_TIMESTAMP),
('victim1', '$2a$10$DowJonesRocks123456789O.u7cX/QlNjN9vQEpgH9fJcHXqrH2i', 'victim1@sara.com', 'Mike Victim', '+1234567893', '321 Need Help Blvd', 'Miami', 'FL', '33101', 'VICTIM', true, CURRENT_TIMESTAMP),
('donor2', '$2a$10$DowJonesRocks123456789O.u7cX/QlNjN9vQEpgH9fJcHXqrH2i', 'donor2@sara.com', 'Sarah Contributor', '+1234567894', '654 Charity Lane', 'Chicago', 'IL', '60601', 'DONOR', true, CURRENT_TIMESTAMP),
('volunteer2', '$2a$10$DowJonesRocks123456789O.u7cX/QlNjN9vQEpgH9fJcHXqrH2i', 'volunteer2@sara.com', 'Bob Helper', '+1234567895', '987 Service Road', 'Houston', 'TX', '77001', 'VOLUNTEER', true, CURRENT_TIMESTAMP);

-- Insert sample resources
INSERT INTO resources (name, description, type, quantity, location, contact_info, status, user_id, verified, created_at, updated_at) VALUES
('Emergency Food Packages', 'Non-perishable food items for families in need', 'FOOD', 50, 'New York Community Center, 456 Generous Ave, New York, NY', 'Contact: +1234567891', 'AVAILABLE', 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Bottled Water Supply', 'Clean drinking water bottles', 'WATER', 200, 'Chicago Relief Center, 654 Charity Lane, Chicago, IL', 'Contact: +1234567894', 'AVAILABLE', 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Winter Blankets', 'Warm blankets for cold weather', 'BLANKETS', 30, 'New York Community Center, 456 Generous Ave, New York, NY', 'Contact: +1234567891', 'AVAILABLE', 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Basic First Aid Kits', 'Medical supplies for minor injuries', 'FIRST_AID', 25, 'Chicago Relief Center, 654 Charity Lane, Chicago, IL', 'Contact: +1234567894', 'AVAILABLE', 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Temporary Shelter Tents', 'Weather-resistant tents for temporary housing', 'SHELTER', 10, 'New York Community Center, 456 Generous Ave, New York, NY', 'Contact: +1234567891', 'AVAILABLE', 2, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample requests
INSERT INTO requests (title, description, resource_type, quantity_needed, location, urgency, status, user_id, created_at, updated_at, needed_by) VALUES
('Urgent Food Assistance', 'Family of 4 needs emergency food supplies after flooding', 'FOOD', 2, '321 Need Help Blvd, Miami, FL 33101', 'HIGH', 'OPEN', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, DATEADD('DAY', 1, CURRENT_TIMESTAMP)),
('Clean Water Needed', 'Community well contaminated, need clean water for 20 families', 'WATER', 100, 'Disaster Zone, Miami, FL', 'CRITICAL', 'OPEN', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, DATEADD('HOUR', 12, CURRENT_TIMESTAMP)),
('Medicine for Elderly', 'Prescription medication for diabetic patient', 'MEDICINE', 1, '321 Need Help Blvd, Miami, FL 33101', 'HIGH', 'OPEN', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, DATEADD('DAY', 2, CURRENT_TIMESTAMP));

-- Insert sample volunteer assignments
INSERT INTO volunteer_assignments (volunteer_id, request_id, status, assigned_at, notes) VALUES
(3, 1, 'ASSIGNED', CURRENT_TIMESTAMP, 'Will deliver food packages tomorrow morning'),
(6, 2, 'IN_PROGRESS', CURRENT_TIMESTAMP, 'Coordinating with local water distribution center');