import logging
logging.basicConfig(level=logging.WARNING,
                    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s')
#format helps identify when, where and what
# don't disable log for anything more specific than warn

logging.debug("this is a debug log")
#above info level so setting level tells you what tracks

logging.info("this is a info log")
logging.warning("this is a warning log")
logging.error("this is a error log")
logging.critical("this is a critical log")