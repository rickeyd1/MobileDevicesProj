#!/usr/bin/env python3

days = ["sunday", "monday", "tuesday","wednesday", "thursday","friday", "saturday"]

print("/* This class was generated by script */")
print("package com.mba.drc.medicalapp;")
print("public class DayGroup {")
for day in days:
	print(f"\tprivate boolean m{day.title()};")
print("")
print("\tDayGroup(){")
for day in days:
	print(f"\t\tm{day.title()} = false;")
print("\t}")
print("")
for day in days:
	print(f"\tboolean {day}(){{return m{day.title()};}}")
print("");
for day in days:
	print(f"\tvoid set{day.title()}(boolean s){{ m{day.title()}=s; }}")
	print(f"\tvoid set{day.title()}(){{ m{day.title()}=true; }}")

print("\tint toInt(){\n\t\tint rv = 0;")
k = 0
for day in days:
	print(f"\t\trv |= (m{day.title()} ? (1<<{k}) : 0);")
	k += 1
print("\t\treturn rv;\n\t}")
	

print("}\n")
