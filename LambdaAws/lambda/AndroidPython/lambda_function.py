def lambda_handler(event, context):
    data = {
        "greetings": "Hello, " + event["firstName"] + " " + event["lastName"] + ".",
        "test": [1, 2, 3],
        "image": "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"
    }
    return data
