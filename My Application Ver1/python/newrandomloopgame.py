import pygame
import sys
import math
import random

pygame.init()

WIDTH, HEIGHT = 600, 600
WHITE, BLACK, RED, GREEN, BLUE, GRAY, BEIGE, DARK_BEIGE = (255, 255, 255), (0, 0, 0), (255, 0, 0), (0, 255, 0), (
0, 0, 255), (169, 169, 169), (245, 245, 220), (222, 184, 135)
FPS = 60
CIRCLE_RADIUS = 200
CIRCLE_THICKNESS = 5

icon = pygame.image.load('helmet_icon_2.png')
icon = pygame.transform.scale(icon, (50, 50))

screen = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("Click in the Colored Zone")
clock = pygame.time.Clock()

font = pygame.font.SysFont(None, 75)
small_font = pygame.font.SysFont(None, 55)

angle = 0
speed_options = [360 / 5, 360 / 4, 360 / 3.5, 360 / 3, 360 / 2.5]
arc_length_range = (10, 60)
speed = random.choice(speed_options)
arc_length = random.randint(*arc_length_range)
score = 0
game_over = False
start_angle = random.randint(0, 360 - arc_length)


def draw_colored_zone(center, radius, start_angle, arc_len, color, border_color, border_thickness):
    start_angle_rad = math.radians(start_angle)
    arc_length_rad = math.radians(arc_len)
    end_angle_rad = start_angle_rad + arc_length_rad

    outer_radius = radius + border_thickness
    inner_radius = radius - border_thickness

    points_outer = []
    for angle in range(int(math.degrees(start_angle_rad)), int(math.degrees(end_angle_rad)) + 1):
        x = center[0] + int(outer_radius * math.cos(math.radians(angle)))
        y = center[1] - int(outer_radius * math.sin(math.radians(angle)))
        points_outer.append((x, y))
    points_outer.append(center)
    pygame.draw.polygon(screen, border_color, points_outer)

    points_inner = []
    for angle in range(int(math.degrees(start_angle_rad)), int(math.degrees(end_angle_rad)) + 1):
        x = center[0] + int(inner_radius * math.cos(math.radians(angle)))
        y = center[1] - int(inner_radius * math.sin(math.radians(angle)))
        points_inner.append((x, y))
    points_inner.append(center)
    pygame.draw.polygon(screen, color, points_inner)

    left_border_outer = (
        center[0] + int(outer_radius * math.cos(start_angle_rad)),
        center[1] - int(outer_radius * math.sin(start_angle_rad))
    )
    left_border_inner = (
        center[0] + int(inner_radius * math.cos(start_angle_rad)),
        center[1] - int(inner_radius * math.sin(start_angle_rad))
    )
    pygame.draw.line(screen, border_color, center, left_border_outer, CIRCLE_THICKNESS // 2)
    pygame.draw.line(screen, border_color, left_border_outer, left_border_inner, CIRCLE_THICKNESS // 2)

    right_border_outer = (
        center[0] + int(outer_radius * math.cos(end_angle_rad)),
        center[1] - int(outer_radius * math.sin(end_angle_rad))
    )
    right_border_inner = (
        center[0] + int(inner_radius * math.cos(end_angle_rad)),
        center[1] - int(inner_radius * math.sin(end_angle_rad))
    )
    pygame.draw.line(screen, border_color, center, right_border_outer, CIRCLE_THICKNESS // 2)
    pygame.draw.line(screen, border_color, right_border_outer, right_border_inner, CIRCLE_THICKNESS // 2)


def draw_moving_icon(center, radius, angle, icon):
    angle_rad = math.radians(angle)
    x = center[0] + int(radius * math.cos(angle_rad))
    y = center[1] - int(radius * math.sin(angle_rad))
    icon_rect = icon.get_rect(center=(x, y))
    screen.blit(icon, icon_rect)


def draw_score(score):
    text = small_font.render(f"Результат: {score}", True, BLACK)
    screen.blit(text, (10, 10))


def reset_zone_and_speed():
    global speed, arc_length, start_angle
    start_angle = random.randint(0, 360 - arc_length)
    speed = random.choice(speed_options)
    arc_length = random.randint(*arc_length_range)


def check_click(center, radius, angle, s_angle, arc_len):
    global game_over, score
    angle = angle % 360
    s_angle = s_angle % 360
    end_angle = (s_angle + arc_len) % 360

    if s_angle < end_angle:
        if s_angle <= angle <= end_angle:
            score += 1
            reset_zone_and_speed()
        else:
            game_over = True
    else:
        if angle >= s_angle or angle <= end_angle:
            score += 1
            reset_zone_and_speed()
        else:
            game_over = True


while True:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            sys.exit()
        if event.type == pygame.MOUSEBUTTONDOWN and not game_over:
            check_click((WIDTH // 2, HEIGHT // 2), CIRCLE_RADIUS, angle, start_angle, arc_length)

    screen.fill(WHITE)

    if not game_over:
        pygame.draw.circle(screen, GRAY, (WIDTH // 2, HEIGHT // 2), CIRCLE_RADIUS, CIRCLE_THICKNESS)

        pygame.draw.circle(screen, BEIGE, (WIDTH // 2, HEIGHT // 2), CIRCLE_RADIUS - CIRCLE_THICKNESS)

        draw_colored_zone((WIDTH // 2, HEIGHT // 2), CIRCLE_RADIUS - CIRCLE_THICKNESS, start_angle, arc_length, GREEN,
                          DARK_BEIGE, CIRCLE_THICKNESS)

        draw_moving_icon((WIDTH // 2, HEIGHT // 2), CIRCLE_RADIUS - CIRCLE_THICKNESS, angle, icon)

        previous_angle = angle
        angle = (angle + speed / FPS) % 360

        if previous_angle > angle:
            reset_zone_and_speed()

    else:
        text = font.render("ХА! лох", True, RED)
        screen.blit(text, (WIDTH // 2 - text.get_width() // 2, HEIGHT // 2 - text.get_height() // 2))

    draw_score(score)

    pygame.display.flip()
    clock.tick(FPS)
