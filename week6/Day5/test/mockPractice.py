from unittest.mock import Mock

fakeWeatherApp = Mock()

fakeWeatherApp.getTemp.return_value = 60

#uses mock
temp = fakeWeatherApp.getTemp()
print(temp)

fakeWeatherApp.getTemp.side_effect = lambda city: 60 if city=="Plano" else 70
temp2 = fakeWeatherApp.getTemp("Plano")
print(temp2)
temp3 = fakeWeatherApp.getTemp("whatever")
print(temp3)


mock = Mock()
mock.api.return_value = {"id": 1, "name": "testMock"}

print(mock.api) #<Mock name='mock.api' id='1936051153744'>
print(mock.api()) #{'id': 1, 'name': 'testMock'}

