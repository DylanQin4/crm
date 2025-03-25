-- First, let's ensure we have a customer_login_info record
INSERT INTO customer_login_info (username, password_set) VALUES
    ('customer1', 1);




-- 10 utilisateurs créés en 2024
INSERT INTO crm.customer (name, phone, address, city, state, country, user_id, description, position, twitter, facebook, youtube, created_at, email, profile_id) VALUES
                                                                                                                                                                     ('John Doe', '1234567890', '123 Main St', 'New York', 'NY', 'USA', 60, '<b>John Doe</b>', 'Manager', NULL, NULL, NULL, '2024-01-15 10:00:00', 'john.doe@gmail.com', 1),
                                                                                                                                                                     ('Jane Smith', '2345678901', '456 Elm St', 'Los Angeles', 'CA', 'USA', 60, '<b>Jane Smith</b>', 'Developer', NULL, NULL, NULL, '2024-02-20 11:30:00', 'jane.smith@gmail.com', 1),
                                                                                                                                                                     ('Alice Johnson', '3456789012', '789 Oak St', 'Chicago', 'IL', 'USA', 60, '<b>Alice Johnson</b>', 'Designer', NULL, NULL, NULL, '2024-03-25 12:45:00', 'alice.johnson@gmail.com', 1),
                                                                                                                                                                     ('Bob Brown', '4567890123', '101 Pine St', 'Houston', 'TX', 'USA', 60, '<b>Bob Brown</b>', 'Analyst', NULL, NULL, NULL, '2024-04-30 14:15:00', 'bob.brown@gmail.com', 1),
                                                                                                                                                                     ('Charlie Davis', '5678901234', '202 Maple St', 'Phoenix', 'AZ', 'USA', 60, '<b>Charlie Davis</b>', 'Engineer', NULL, NULL, NULL, '2024-05-05 15:30:00', 'charlie.davis@gmail.com', 1),
                                                                                                                                                                     ('Eva Wilson', '6789012345', '303 Birch St', 'Philadelphia', 'PA', 'USA', 60, '<b>Eva Wilson</b>', 'Consultant', NULL, NULL, NULL, '2024-06-10 16:45:00', 'eva.wilson@gmail.com', 1),
                                                                                                                                                                     ('Frank Miller', '7890123456', '404 Cedar St', 'San Antonio', 'TX', 'USA', 60, '<b>Frank Miller</b>', 'Architect', NULL, NULL, NULL, '2024-07-15 17:00:00', 'frank.miller@gmail.com', 1),
                                                                                                                                                                     ('Grace Lee', '8901234567', '505 Spruce St', 'San Diego', 'CA', 'USA', 60, '<b>Grace Lee</b>', 'Product Manager', NULL, NULL, NULL, '2024-08-20 18:15:00', 'grace.lee@gmail.com', 1),
                                                                                                                                                                     ('Henry Garcia', '9012345678', '606 Fir St', 'Dallas', 'TX', 'USA', 60, '<b>Henry Garcia</b>', 'Data Scientist', NULL, NULL, NULL, '2024-09-25 19:30:00', 'henry.garcia@gmail.com', 1),
                                                                                                                                                                     ('Ivy Martinez', '0123456789', '707 Redwood St', 'San Jose', 'CA', 'USA', 60, '<b>Ivy Martinez</b>', 'Marketing Specialist', NULL, NULL, NULL, '2024-10-30 20:45:00', 'ivy.martinez@gmail.com', 1);

-- 10 utilisateurs créés entre janvier et mars 2025
INSERT INTO crm.customer (name, phone, address, city, state, country, user_id, description, position, twitter, facebook, youtube, created_at, email, profile_id) VALUES
                                                                                                                                                                     ('Jack Robinson', '1234509876', '808 Sequoia St', 'Austin', 'TX', 'USA', 60, '<b>Jack Robinson</b>', 'Sales Executive', NULL, NULL, NULL, '2025-01-05 09:00:00', 'jack.robinson@gmail.com', 1),
                                                                                                                                                                     ('Karen Clark', '2345610987', '909 Willow St', 'Jacksonville', 'FL', 'USA', 60, '<b>Karen Clark</b>', 'HR Manager', NULL, NULL, NULL, '2025-01-10 10:15:00', 'karen.clark@gmail.com', 1),
                                                                                                                                                                     ('Leo Rodriguez', '3456721098', '1010 Palm St', 'San Francisco', 'CA', 'USA', 60, '<b>Leo Rodriguez</b>', 'Software Engineer', NULL, NULL, NULL, '2025-01-15 11:30:00', 'leo.rodriguez@gmail.com', 1),
                                                                                                                                                                     ('Mia Hernandez', '4567832109', '1111 Olive St', 'Columbus', 'OH', 'USA', 60, '<b>Mia Hernandez</b>', 'UX Designer', NULL, NULL, NULL, '2025-01-20 12:45:00', 'mia.hernandez@gmail.com', 1),
                                                                                                                                                                     ('Noah Young', '5678943210', '1212 Pineapple St', 'Charlotte', 'NC', 'USA', 60, '<b>Noah Young</b>', 'Business Analyst', NULL, NULL, NULL, '2025-01-25 14:00:00', 'noah.young@gmail.com', 1),
                                                                                                                                                                     ('Olivia King', '6789054321', '1313 Mango St', 'Indianapolis', 'IN', 'USA', 60, '<b>Olivia King</b>', 'Project Manager', NULL, NULL, NULL, '2025-02-01 15:15:00', 'olivia.king@gmail.com', 1),
                                                                                                                                                                     ('Peter Wright', '7890165432', '1414 Banana St', 'Seattle', 'WA', 'USA', 60, '<b>Peter Wright</b>', 'DevOps Engineer', NULL, NULL, NULL, '2025-02-10 16:30:00', 'peter.wright@gmail.com', 1),
                                                                                                                                                                     ('Quinn Lopez', '8901276543', '1515 Peach St', 'Denver', 'CO', 'USA', 60, '<b>Quinn Lopez</b>', 'Financial Analyst', NULL, NULL, NULL, '2025-02-15 17:45:00', 'quinn.lopez@gmail.com', 1),
                                                                                                                                                                     ('Rachel Scott', '9012387654', '1616 Cherry St', 'Washington', 'DC', 'USA', 60, '<b>Rachel Scott</b>', 'Content Writer', NULL, NULL, NULL, '2025-03-01 18:00:00', 'rachel.scott@gmail.com', 1),
                                                                                                                                                                     ('Samuel Green', '0123498765', '1717 Apple St', 'Boston', 'MA', 'USA', 60, '<b>Samuel Green</b>', 'System Administrator', NULL, NULL, NULL, '2025-03-10 19:15:00', 'samuel.green@gmail.com', 1);




-- Insert 20 sample customers
INSERT INTO customer (name, phone, address, city, state, country, user_id, description, position, twitter, facebook, youtube, created_at, email, profile_id) VALUES
    ('John Doe', '1234567890', '123 Main St', 'New York', 'NY', 'USA', 60, '<b>John Doe</b>', 'Manager', NULL, NULL, NULL, '2024-01-15 10:00:00', 'john.doe@gmail.com', LAST_INSERT_ID()),
    ('Jane Smith', '2345678901', '456 Elm St', 'Los Angeles', 'CA', 'USA', 60, '<b>Jane Smith</b>', 'Developer', NULL, NULL, NULL, '2024-02-20 11:30:00', 'jane.smith@gmail.com', LAST_INSERT_ID()),
    ('Alice Johnson', '3456789012', '789 Oak St', 'Chicago', 'IL', 'USA', 60, '<b>Alice Johnson</b>', 'Designer', NULL, NULL, NULL, '2024-03-25 12:45:00', 'alice.johnson@gmail.com', LAST_INSERT_ID()),
    ('Bob Brown', '4567890123', '101 Pine St', 'Houston', 'TX', 'USA', 60, '<b>Bob Brown</b>', 'Analyst', NULL, NULL, NULL, '2024-04-30 14:15:00', 'bob.brown@gmail.com', LAST_INSERT_ID()),
    ('Charlie Davis', '5678901234', '202 Maple St', 'Phoenix', 'AZ', 'USA', 60, '<b>Charlie Davis</b>', 'Engineer', NULL, NULL, NULL, '2024-05-05 15:30:00', 'charlie.davis@gmail.com', LAST_INSERT_ID());

-- Insert budgets with a subquery to get valid customer IDs
INSERT INTO budgets (budget, date_min, date_max, designation, status, customer_id)
SELECT 50000.00, '2023-01-01', '2023-12-31', 'Annual IT Infrastructure', 'ACTIVE', customer_id FROM customer ORDER BY customer_id LIMIT 1;

INSERT INTO budgets (budget, date_min, date_max, designation, status, customer_id)
SELECT 25000.00, '2023-02-01', '2023-08-31', 'Q1-Q3 Marketing Campaign', 'ACTIVE', customer_id FROM customer ORDER BY customer_id LIMIT 1, 1;

INSERT INTO budgets (budget, date_min, date_max, designation, status, customer_id)
SELECT 100000.00, '2023-03-01', '2024-02-28', 'Store Renovation Project', 'ACTIVE', customer_id FROM customer ORDER BY customer_id LIMIT 2, 1;

INSERT INTO budgets (budget, date_min, date_max, designation, status, customer_id)
SELECT 15000.00, '2023-04-01', '2023-09-30', 'Product Development', 'ACTIVE', customer_id FROM customer ORDER BY customer_id LIMIT 3, 1;

INSERT INTO budgets (budget, date_min, date_max, designation, status, customer_id)
SELECT 75000.00, '2023-05-01', '2023-12-31', 'Financial System Upgrade', 'ACTIVE', customer_id FROM customer ORDER BY customer_id LIMIT 4, 1;

-- Insert tickets with dynamic customer IDs
INSERT INTO trigger_ticket (subject, description, status, priority, customer_id, created_at)
SELECT
    'Server Downtime Issue',
    'Experiencing periodic server downtime affecting operations',
    'open',
    'high',
    customer_id,
    '2023-01-20'
FROM customer ORDER BY customer_id LIMIT 1;

INSERT INTO trigger_ticket (subject, description, status, priority, customer_id, created_at)
SELECT
    'Website Redesign Request',
    'Need complete redesign of company website',
    'assigned',
    'medium',
    customer_id,
    '2023-02-25'
FROM customer ORDER BY customer_id LIMIT 1, 1;

INSERT INTO trigger_ticket (subject, description, status, priority, customer_id, created_at)
SELECT
    'POS System Failure',
    'Point of sale system crashes during peak hours',
    'in-progress',
    'high',
    customer_id,
    '2023-03-15'
FROM customer ORDER BY customer_id LIMIT 2, 1;

INSERT INTO trigger_ticket (subject, description, status, priority, customer_id, created_at)
SELECT
    'Packaging Design Update',
    'Request for eco-friendly packaging design',
    'open',
    'low',
    customer_id,
    '2023-04-10'
FROM customer ORDER BY customer_id LIMIT 3, 1;

INSERT INTO trigger_ticket (subject, description, status, priority, customer_id, created_at)
SELECT
    'Financial Software Bug',
    'Critical calculation error in financial reporting module',
    'in-progress',
    'high',
    customer_id,
    '2023-05-15'
FROM customer ORDER BY customer_id LIMIT 4, 1;

-- Insert leads with dynamic customer IDs
INSERT INTO trigger_lead (customer_id, name, phone, status, created_at)
SELECT
    customer_id,
    'Server Infrastructure Upgrade',
    '123-456-7890',
    'new',
    '2023-01-25'
FROM customer ORDER BY customer_id LIMIT 1;

INSERT INTO trigger_lead (customer_id, name, phone, status, created_at)
SELECT
    customer_id,
    'Digital Marketing Campaign',
    '987-654-3210',
    'in-progress',
    '2023-02-28'
FROM customer ORDER BY customer_id LIMIT 1, 1;

INSERT INTO trigger_lead (customer_id, name, phone, status, created_at)
SELECT
    customer_id,
    'Store Management Software',
    '555-123-4567',
    'qualified',
    '2023-03-20'
FROM customer ORDER BY customer_id LIMIT 2, 1;

INSERT INTO trigger_lead (customer_id, name, phone, status, created_at)
SELECT
    customer_id,
    'Sustainable Packaging Solution',
    '444-555-6666',
    'new',
    '2023-04-15'
FROM customer ORDER BY customer_id LIMIT 3, 1;

INSERT INTO trigger_lead (customer_id, name, phone, status, created_at)
SELECT
    customer_id,
    'Financial Analytics Platform',
    '777-888-9999',
    'in-progress',
    '2023-05-20'
FROM customer ORDER BY customer_id LIMIT 4, 1;

-- Insert expenses for tickets with dynamic IDs
INSERT INTO expenses (amount, label, date_expense, ticket_id, lead_id, budget_id)
SELECT
    5000.00,
    'Server hardware replacement',
    '2023-01-22',
    t.ticket_id,
    NULL,
    b.id
FROM
    trigger_ticket t,
    budgets b
WHERE
    t.subject = 'Server Downtime Issue' AND
    b.designation = 'Annual IT Infrastructure'
LIMIT 1;

INSERT INTO expenses (amount, label, date_expense, ticket_id, lead_id, budget_id)
SELECT
    3500.00,
    'Website design initial payment',
    '2023-02-27',
    t.ticket_id,
    NULL,
    b.id
FROM
    trigger_ticket t,
    budgets b
WHERE
    t.subject = 'Website Redesign Request' AND
    b.designation = 'Q1-Q3 Marketing Campaign'
LIMIT 1;

INSERT INTO expenses (amount, label, date_expense, ticket_id, lead_id, budget_id)
SELECT
    4200.00,
    'POS system repair service',
    '2023-03-17',
    t.ticket_id,
    NULL,
    b.id
FROM
    trigger_ticket t,
    budgets b
WHERE
    t.subject = 'POS System Failure' AND
    b.designation = 'Store Renovation Project'
LIMIT 1;

-- Insert expenses for leads with dynamic IDs
INSERT INTO expenses (amount, label, date_expense, ticket_id, lead_id, budget_id)
SELECT
    4500.00,
    'Infrastructure assessment',
    '2023-01-27',
    NULL,
    l.lead_id,
    b.id
FROM
    trigger_lead l,
    budgets b
WHERE
    l.name = 'Server Infrastructure Upgrade' AND
    b.designation = 'Annual IT Infrastructure'
LIMIT 1;

INSERT INTO expenses (amount, label, date_expense, ticket_id, lead_id, budget_id)
SELECT
    3200.00,
    'Marketing strategy development',
    '2023-03-02',
    NULL,
    l.lead_id,
    b.id
FROM
    trigger_lead l,
    budgets b
WHERE
    l.name = 'Digital Marketing Campaign' AND
    b.designation = 'Q1-Q3 Marketing Campaign'
LIMIT 1;

INSERT INTO expenses (amount, label, date_expense, ticket_id, lead_id, budget_id)
SELECT
    4800.00,
    'Software demonstration setup',
    '2023-03-22',
    NULL,
    l.lead_id,
    b.id
FROM
    trigger_lead l,
    budgets b
WHERE
    l.name = 'Store Management Software' AND
    b.designation = 'Store Renovation Project'
LIMIT 1;