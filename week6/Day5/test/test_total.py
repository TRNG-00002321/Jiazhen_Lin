import unittest
from functools import reduce
from unittest.mock import Mock, MagicMock, patch

import src.total as total

class test_fileMethods(unittest.TestCase):
    def test_calculate_total(self):
        total.read = MagicMock()
        total.read.return_value = [1, 2, 3]
        result = total.calculate_total("")
        self.assertEqual(result, 6)
        total.read.assert_called_once_with('')


    def test_calculate_test_path(self):
        with patch("src.total.read") as mock_read_file:
            mock_read_file.return_value = [1, 2, 3]
            result = total.calculate_total("")
            self.assertEqual(result, 6)


    @patch("src.total.read")
    def test_calculate_total_patch_decorator(self, mock_calculate_total):
        mock_calculate_total.return_value = [1, 2, 3]
        result = total.calculate_total("")
        self.assertEqual(result, 6)
        mock_calculate_total.assert_called_once_with('')



    def multiply_values(self, fileName):
        values = total.read(fileName)
        return reduce(lambda x, y: x * y, values)

    @patch("src.total.read")
    def test_multiply_values(self, mock_read_value):
        mock_read_value.return_value = [1, 2, 3, 4]
        with patch("src.total.calculate_total", side_effect=self.multiply_values):
            result = total.calculate_total("")
            self.assertEqual(result, 24)
            mock_read_value.assert_called_once_with('')


    #If in a list, there is a negative number then raises an exception and patch it with side effect
    def neg_check_side_effect(self, value):
        if any(v<0 for v in value):
            raise ValueError("Negative values not allowed")
        return sum(value)