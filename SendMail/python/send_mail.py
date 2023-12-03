from email import message
import smtplib

import config


smtp_host = config.smtp_host_y
smtp_port = config.smtp_port
from_email = config.from_email_y
to_email = config.to_email
username = config.username_y
password = config.password_y

msg = message.EmailMessage()
msg.set_content('Test mail from yahoo mail')
msg['Subject'] = 'Test Yahoo!mail'
msg['From'] = from_email
msg['To'] = to_email

server = smtplib.SMTP(smtp_host, smtp_port)
server.ehlo()
server.login(username, password)
server.send_message(msg)
server.quit()
