import unittest
from unittest.mock import Mock
from parameterized import parameterized
import src.alerts as alerts
class Test(unittest.TestCase):
    @parameterized.expand([
        [1, True],
        [10, True],
        [100, False],
        [120, False],
        [130, True],
    ])
    def test_alert(self, fakeSpeed, expecetAlert):
        alerts.speed = Mock(return_value=fakeSpeed)
        tempAlert = alerts.alert()
        #print(alert.speed())
        self.assertEqual(tempAlert, expecetAlert)
        alerts.speed.assert_called_once()