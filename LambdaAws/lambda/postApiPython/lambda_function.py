import json

def lambda_handler(event, context):
    # イベントとコンテキストの内容を出力
    print("event:", event)
    print("context:", context)

    # Check if 'param1' and 'param2' are present in the event data
    if 'param1' in event and 'param2' in event:
        # Access the parameters
        strParam1 = event['param1']
        strParam2 = event['param2']

        # パラメータの内容を出力する
        print("strParam1:", strParam1)
        print("strParam2:", strParam2)

        return {
            'statusCode': 200,
            'body': json.dumps('ok')
        }

    # If 'param1' and 'param2' are not present, or any other issue
    return {
        'statusCode': 400,
        'body': json.dumps({'error': 'Bad Request: Missing parameters'})
    }
