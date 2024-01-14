exports.handler = async (event, context, callback) => {
    var data = {
       "greetings": "Hello, " + event.firstName + " " + event.lastName + "."
    };
    callback(null, data);
};