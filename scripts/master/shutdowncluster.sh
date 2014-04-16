#!/bin/sh

ssh hduser@titanium "sudo shutdown -h now"
ssh hduser@tungsten "sudo shutdown -h now"
ssh hduser@silicon "sudo shutdown -h now"
sudo shutdown -h now
