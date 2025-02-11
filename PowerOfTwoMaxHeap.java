import pandas as pd

# Load your data (replace 'your_file.csv' with the actual path)
df = pd.read_csv('your_file.csv')

# --- Data Cleaning (Important!) ---
# Check for missing values and handle them appropriately.
# Options: Fill with 0, mean, median, or drop rows with missing data.
print(df.isnull().sum()) # Check for missing values
df.fillna(0, inplace=True)  # Example: Fill NaN with 0.  Customize as needed!

# Convert relevant columns to numeric (if they aren't already)
numeric_cols = ['Total Revenue', 'Net Income', 'Total Assets', 'Total Liabilities', 'Cash Flow from Operating Activities']
for col in numeric_cols:
    df[col] = pd.to_numeric(df[col], errors='coerce')  # errors='coerce' handles non-numeric values gracefully


# --- Calculate Year-over-Year Changes ---
df['Revenue Growth (%)'] = df.groupby(['Company'])['Total Revenue'].pct_change() * 100
df['Net Income Growth (%)'] = df.groupby(['Company'])['Net Income'].pct_change() * 100
df['Assets Growth (%)'] = df.groupby(['Company'])['Total Assets'].pct_change() * 100
df['Liabilities Growth (%)'] = df.groupby(['Company'])['Total Liabilities'].pct_change() * 100
df['Cash Flow Growth (%)'] = df.groupby(['Company'])['Cash Flow from Operating Activities'].pct_change() * 100

# --- Analyze Trends and Insights ---
# Example: Average revenue growth for each company
print("\nAverage Revenue Growth:")
print(df.groupby('Company')['Revenue Growth (%)'].mean())

# Example: Net income trend over the years for each company
print("\nNet Income Trend:")
print(df.pivot(index='Fiscal Year', columns='Company', values='Net Income'))

# Example: Visualize Revenue Growth (using matplotlib)
import matplotlib.pyplot as plt
plt.figure(figsize=(10, 6))
for company in df['Company'].unique():
    company_data = df[df['Company'] == company]
    plt.plot(company_data['Fiscal Year'], company_data['Revenue Growth (%)'], label=company)
plt.xlabel('Fiscal Year')
plt.ylabel('Revenue Growth (%)')
plt.title('Revenue Growth Over Time')
plt.legend()
plt.grid(True)
plt.show()

# Add more analysis and visualizations as needed...

# --- Summarize Findings (using Markdown cells) ---
# Use Markdown cells in your Jupyter Notebook to explain your observations.
# For example:
# ## Revenue Analysis
# "Microsoft has shown consistent revenue growth over the past three years..."

#... add your interpretations and insights here...