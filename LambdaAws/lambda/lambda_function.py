def lambda_handler(event, context):
    data = {
        "greetings": "Hello, " + event["firstName"] + " " + event["lastName"] + ".",
        "test": [1, 2, 3]
    }
    return data
